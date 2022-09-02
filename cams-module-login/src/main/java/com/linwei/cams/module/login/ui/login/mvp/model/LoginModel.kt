package com.linwei.cams.module.login.ui.login.mvp.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.login.provider.LoginProviderImpl
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.UserInfoBean

class LoginModel : MvpModel(), ILoginModel {

    private val mLoginProvider: LoginProviderImpl=LoginProviderImpl()

    override fun login(userName: String, passWord: String, callback: ResponseCallback<UserInfoBean>) {
        mLoginProvider.login(userName, passWord, callback)
    }

    override fun register(
        userName: String,
        passWord: String,
        rePassWord: String,
        callback: ResponseCallback<UserInfoBean>
    ) {
        mLoginProvider.register(userName, passWord, rePassWord, callback)
    }

    override fun logout(callback: ResponseCallback<Any>) {
        mLoginProvider.logout(callback)
    }
}