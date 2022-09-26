package com.linwei.cams.component.network

import com.linwei.cams.component.network.annotation.RetryCount
import com.linwei.cams.component.network.annotation.RetryDelay
import com.linwei.cams.component.network.annotation.RetryIncreaseDelay
import com.linwei.cams.component.network.callback.ApiCallback
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.factory.rxjava.CallEnqueueObservable
import com.linwei.cams.component.network.factory.rxjava.RetryHandler
import com.linwei.cams.component.network.model.ApiResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class ApiCall<R> internal constructor(
    annotations: Array<Annotation>,
    private var call: Call<ApiResponse<R>>
) {
    private var mRetryCount = 0
    private var mRetryDelay: Long = 0
    private var mRetryIncreaseDelay: Long = 0

    private val mEnqueueObservable: Observable<Response<ApiResponse<R>>> =
        RxJavaPlugins.onAssembly(CallEnqueueObservable(call))

    private var mDisposable: Disposable? = null

    init {
        for (annotation in annotations) {
            val clazz: Class<out Annotation> = annotation.javaClass
            if (clazz == RetryCount::class.java) {
                val retryCount: RetryCount = annotation as RetryCount
                mRetryCount = retryCount.value
            } else if (clazz == RetryDelay::class.java) {
                val retryDelay: RetryDelay = annotation as RetryDelay
                mRetryDelay = retryDelay.value
            } else if (clazz == RetryIncreaseDelay::class.java) {
                val retryIncreaseDelay: RetryIncreaseDelay = annotation as RetryIncreaseDelay
                mRetryIncreaseDelay = retryIncreaseDelay.value
            }
        }
    }

    /**
     * 进入请求队列
     * @param callback     请求回调
     */
    fun <T : ApiCallback<R>?> enqueue(
        callback: T?
    ) {
        /*if (activity instanceof RxAppCompatActivity) {
            RxAppCompatActivity rxAppCompatActivity = (RxAppCompatActivity) activity;
            observable = mEnqueueObservable.compose(rxAppCompatActivity.<Response<ApiResponse<R>>>bindToLifecycle());
        } else {
            observable = mEnqueueObservable;
        }*/
        val observable: Observable<Response<ApiResponse<R>>> = mEnqueueObservable
        mDisposable =
            observable.retryWhen(RetryHandler(mRetryCount, mRetryDelay, mRetryIncreaseDelay))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { callback?.onStart() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<Response<ApiResponse<R>>> {
                    override fun accept(t: Response<ApiResponse<R>>) {
                        @Throws(Exception::class)
                        fun accept(response: Response<ApiResponse<R>?>) {
                            val data: ApiResponse<R>? = response.body()
                            if (!response.isSuccessful || data == null) {
                                callback?.onFailure(response.code(), response.message())
                                cancel()
                                return
                            }

                            if (ApiConstants.REQUEST_SUCCESS == data.errorCode) {
                                if (null != data.data) {
                                    callback?.onSuccess(data.errorCode, data.data)
                                } else {
                                    callback?.onFailure(ApiConstants.EMPTY_DATA_ERROR, "")
                                }
                            } else {
                                callback?.onFailure(data.errorCode, data.errorMsg)
                            }
                            cancel()
                        }
                    }
                }) { throwable ->
                    onFailure(callback, throwable)
                    cancel()
                }
    }

    /**
     * Synchronously send the request and return its response.
     *
     * @throws IOException if a problem occurred talking to the server.
     */
    @Throws(IOException::class)
    fun exectue(): Response<ApiResponse<R>> {
        return call.clone().execute()
    }

    /**
     * 处理错误
     *
     * @param callback  回调
     * @param throwable 错误
     */
    private fun onFailure(callback: ApiCallback<R>?, throwable: Throwable) {
        val exception = ApiException.handleException(throwable)
        callback?.onFailure(exception.code, exception.displayMessage)
    }

    fun cancel() {
        mDisposable?.dispose()
    }
}