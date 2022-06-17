package com.linwei.cams.module.login.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.callback.ApiCallback
import com.linwei.cams.module.login.http.ApiService
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.login.LoginRouterTable
import com.linwei.cams.service.login.model.UserInfoBean
import com.linwei.cams.service.login.provider.LoginProvider
import javax.inject.Inject

@Route(path = LoginRouterTable.PATH_SERVICE_LOGIN)
class LoginProviderImpl @Inject constructor(private val apiService: ApiService) : LoginProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    override fun login(userName: String, passWord: String, callback: ResponseCallback<UserInfoBean>) {
        apiService.login(userName, passWord).enqueue(object : ApiCallback<UserInfoBean> {
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
        apiService.register(userName, passWord, rePassWord)
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
        apiService.logout().enqueue(object : ApiCallback<Any> {
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