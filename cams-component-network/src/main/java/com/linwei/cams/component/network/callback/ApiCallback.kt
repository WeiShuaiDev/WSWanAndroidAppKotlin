package com.linwei.cams.component.network.callback


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
    fun onSuccess(code: Int?, data: T)

    /**
     * 加载失败
     *
     * @param t 异常
     */
    fun onFailure(code: Int?, message: String?)
}