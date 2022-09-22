package com.linwei.cams.module.common.ktx

import com.linwei.cams.component.network.ApiCall
import com.linwei.cams.component.network.callback.ApiCallback
import com.linwei.cams.component.network.callback.ErrorConsumer
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.component.network.transformer.ResponseTransformer
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import io.reactivex.rxjava3.core.Observable


fun <T> Observable<ApiResponse<T>>.networks(callback: ResponseCallback<T>) {
    compose(ResponseTransformer.obtain())
        .subscribe({ data ->
            callback.onSuccess(data)
        }, object : ErrorConsumer() {
            override fun error(e: ApiException) {
                callback.onFailed(ErrorMessage(e.code, e.displayMessage))
            }
        })
}

fun <T> ApiCall<T>.networks(callback: ResponseCallback<T>) {
    enqueue(object : ApiCallback<T> {
        override fun onStart() {
        }

        override fun onSuccess(code: Int?, data: T) {
            callback.onSuccess(data)
        }

        override fun onFailure(code: Int?, message: String?) {
            callback.onFailed(ErrorMessage(code, message))
        }
    })
}


