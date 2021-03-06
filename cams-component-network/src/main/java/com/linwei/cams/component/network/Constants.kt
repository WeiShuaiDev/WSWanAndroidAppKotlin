package com.linwei.cams.component.network

object ApiConstants {
    //成功状态码
    const val REQUEST_SUCCESS: Int = 0

    //失败状态码
    const val REQUEST_FAILURE: Int = -1

    //500状态码
    const val CODE_500: Int = 500

    //404状态码
    const val CODE_404: Int = 404

    //403状态码
    const val CODE_403: Int = 403

    //307状态码
    const val CODE_307: Int = 307

    //未知错误
    const val UNKNOWN: Int = 10001

    //解析错误
    const val PARSE_ERROR: Int = 10002

    //网络错误/连接错误
    const val NETWORK_ERROR: Int = 10003

    //线程错误
    const val THREAD_ERROR: Int = 10004

    //空数据
    const val EMPTY_DATA_ERROR: Int = 10005

}