package com.linwei.cams.component.network.exception

import android.net.ParseException
import android.os.NetworkOnMainThreadException
import com.google.gson.JsonParseException
import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.component.network.ShowTxtConstants
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException


class ApiException(var code: Int, var displayMessage: String?) : Exception() {

    companion object {
        fun handleException(throwable: Throwable): ApiException {
            return if (throwable is UnknownHostException) {
                //网络错误
                ApiException(ApiConstants.NETWORK_ERROR, ShowTxtConstants.NETWORK_UNAVAILABLE_TXT)
            } else if (throwable is SocketTimeoutException) {
                //网络错误
                ApiException(
                    ApiConstants.NETWORK_ERROR,
                    ShowTxtConstants.REQUEST_NETWORK_TIMEOUT_TXT
                )
            } else if (throwable is HttpException) {
                //网络错误
                ApiException(ApiConstants.NETWORK_ERROR, convertStatusCode(throwable))
            } else if (throwable is JsonParseException
                || throwable is JSONException
                || throwable is ParseException
            ) {
                //解析错误
                ApiException(ApiConstants.PARSE_ERROR, ShowTxtConstants.DATA_ANALYSIS_ERROR_TXT)
            } else if (throwable is ConnectException) {
                //网络错误
                ApiException(ApiConstants.NETWORK_ERROR, ShowTxtConstants.NETWORK_UNAVAILABLE_TXT)
            } else if (throwable is NetworkOnMainThreadException) {
                ApiException(
                    ApiConstants.THREAD_ERROR,
                    ShowTxtConstants.NETWORK_REQUEST_MAIN_THREAD_TXT
                )
            } else if (throwable is IllegalArgumentException || throwable is UnknownServiceException) {
                //未知错误
                ApiException(ApiConstants.UNKNOWN, throwable.message)
            } else {
                //未知错误
                ApiException(ApiConstants.UNKNOWN, ShowTxtConstants.UNkNOWN_ERROR_TXT)
            }
        }

        private fun convertStatusCode(httpException: HttpException): String {
            val msg: String = when {
                httpException.code() == ApiConstants.CODE_500 -> {
                    ShowTxtConstants.SERVER_ERROR_TXT
                }
                httpException.code() == ApiConstants.CODE_404 -> {
                    ShowTxtConstants.REQUEST_ADDRESS_NOT_EXIST_TXT
                }
                httpException.code() == ApiConstants.CODE_403 -> {
                    ShowTxtConstants.REQUEST_SERVICE_REJECT_TXT
                }
                httpException.code() == ApiConstants.CODE_307 -> {
                    ShowTxtConstants.REQUEST_REDIRECTED_OTHER_PAGE_TXT
                }
                else -> {
                    httpException.message()
                }
            }
            return msg
        }
    }


}
