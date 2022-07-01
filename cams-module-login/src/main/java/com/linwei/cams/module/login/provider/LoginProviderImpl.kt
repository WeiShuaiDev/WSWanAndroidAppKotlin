package com.linwei.cams.module.login.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.callback.ApiCallback
import com.linwei.cams.module.login.http.ApiService
import com.linwei.cams.module.login.http.ApiServiceWrap
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.login.LoginRouterTable
import com.linwei.cams.service.login.model.UserInfoBean
import com.linwei.cams.service.login.provider.LoginProvider
import javax.inject.Inject

@Route(path = LoginRouterTable.PATH_SERVICE_LOGIN)
class LoginProviderImpl @Inject constructor() : LoginProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[LoginProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())

    override fun login(userName: String, passWord: String, callback: ResponseCallback<UserInfoBean>) {
        mApiService.login(userName, passWord).enqueue(object : ApiCallback<UserInfoBean> {
            override fun onStart() {

            }

            override fun onSuccess(code: Int?, data: UserInfoBean) {
                callback.onSuccess(data)
            }

            override fun onFailure(code: Int?, message: String?) {
                callback.onFailed(ErrorMessage(code, message))
            }
        })
    }

    override fun register(
        userName: String,
        passWord: String,
        rePassWord: String,
        callback: ResponseCallback<UserInfoBean>
    ) {
        mApiService.register(userName, passWord, rePassWord)
            .enqueue(object : ApiCallback<UserInfoBean> {
                override fun onStart() {

                }

                override fun onSuccess(code: Int?, data: UserInfoBean) {
                    callback.onSuccess(data)
                }

                override fun onFailure(code: Int?, message: String?) {
                    callback.onFailed(ErrorMessage(code, message))
                }
            })
    }

    override fun logout(callback: ResponseCallback<Any>) {
        mApiService.logout().enqueue(object : ApiCallback<Any> {
            override fun onStart() {

            }

            override fun onSuccess(code: Int?, data: Any) {
                callback.onSuccess(data)
            }

            override fun onFailure(code: Int?, message: String?) {
                callback.onFailed(ErrorMessage(code, message))
            }
        })
    }
}