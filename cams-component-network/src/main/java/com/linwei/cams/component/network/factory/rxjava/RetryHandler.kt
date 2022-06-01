package com.linwei.cams.component.network.factory.rxjava

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function

class RetryHandler(
    private val retryCount: Int,
    private val retryDelay: Long,
    private val increaseDelay: Long
) : Function<Observable<Throwable>, Observable<Long>> {

    override fun apply(throwableObservable: Observable<Throwable>): Observable<Long> {
        return throwableObservable.zipWith(Observable.range(1, retryCount + 1)
        ) { throwable, integer -> Wrapper(throwable, integer) }.flatMap(object : Function<Wrapper, Observable<Long>> {
                override fun apply(wrapper: Wrapper): Observable<Long> {
                    //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    if (canRetry(wrapper)) {
                        return Observable.timer(
                            retryDelay + (wrapper.index - 1) * increaseDelay,
                            TimeUnit.MILLISECONDS
                        )
                    }
                    return Observable.error(wrapper.throwable);
                }
        })
    }

    private fun canRetry(wrapper: Wrapper): Boolean {
        return (wrapper.throwable is ConnectException || wrapper.throwable is SocketTimeoutException ||
                wrapper.throwable is UnknownHostException ||
                wrapper.throwable is TimeoutException) && wrapper.index < retryCount + 1
    }
}

class Wrapper(val throwable: Throwable, val index: Int)