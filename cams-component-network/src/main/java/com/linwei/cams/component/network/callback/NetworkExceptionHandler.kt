package com.linwei.cams.component.network.callback

import com.linwei.cams.component.network.exception.ApiException
import kotlinx.coroutines.CoroutineExceptionHandler
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

class NetworkExceptionHandler(
    private val onException: (e: ApiException) -> Unit = {}
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        val apiException = ApiException.handleException(exception)
        onException.invoke(apiException)
    }
}