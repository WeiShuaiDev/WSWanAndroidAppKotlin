package com.linwei.cams.component.network.factory.rxjava

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.exceptions.CompositeException
import io.reactivex.rxjava3.exceptions.Exceptions
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class CallEnqueueObservable<T>(private val originalCall: Call<T>) :
    Observable<Response<T>>() {

    override fun subscribeActual(observer: Observer<in Response<T>>) {
        // Since Call is a one-shot type, clone it for each new observer.
        val call = originalCall.clone()
        val callback = CallCallback(call, observer)
        observer.onSubscribe(callback)
        if (!callback.isDisposed) {
            call.enqueue(callback)
        }
    }

    private class CallCallback<T>(
        private val call: Call<*>,
        private val observer: Observer<in Response<T>>
    ) : Disposable, Callback<T> {

        private var terminated = false

        @Volatile
        private var isDisposed = false
            private set

        override fun isDisposed(): Boolean = isDisposed

        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (isDisposed) {
                return
            }
            try {
                observer.onNext(response)
                if (!isDisposed) {
                    terminated = true
                    observer.onComplete()
                }
            } catch (t: Exception) {
                if (terminated) {
                    RxJavaPlugins.onError(t)
                } else if (!isDisposed) {
                    try {
                        observer.onError(t)
                    } catch (inner: Exception) {
                        Exceptions.throwIfFatal(inner)
                        RxJavaPlugins.onError(CompositeException(t, inner))
                    }
                }
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            if (call.isCanceled) {
                return
            }
            try {
                observer.onError(t)
            } catch (inner: Exception) {
                Exceptions.throwIfFatal(inner)
                RxJavaPlugins.onError(CompositeException(t, inner))
            }
        }

        override fun dispose() {
            isDisposed = true
            call.cancel()
        }
    }
}