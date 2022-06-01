package com.linwei.cams.component.network.callback

import com.linwei.cams.component.network.model.ApiResponse

interface ApiCallback<T> {
    /**
     * 开始加载
     */
    fun onStart()

    /**
     * 加载成功
     *
     * @param response 接口回调
     */
    fun onSuccess(response: ApiResponse<T>)

    /**
     * 加载失败
     *
     * @param t 异常
     */
    fun onError(t: Throwable)
}