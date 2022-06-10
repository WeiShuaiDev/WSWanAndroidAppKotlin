package com.linwei.cams.module.login.ui.login.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.login.provider.LoginProviderImpl
import com.linwei.cams.module.login.ui.login.contract.ILoginModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.login.model.UserInfo
import javax.inject.Inject

class LoginModel : MvpModel(), ILoginModel {

    @Inject
    lateinit var mLoginProvider: LoginProviderImpl

    override fun login(userName: String, passWord: String, callback: ResponseCallback<UserInfo>) {
        mLoginProvider.login(userName, passWord, callback)
    }

    override fun register(
        userName: String,
        passWord: String,
        rePassWord: String,
        callback: ResponseCallback<UserInfo>
    ) {
        mLoginProvider.register(userName, passWord, rePassWord, callback)
    }

    override fun logout(callback: ResponseCallback<Any>) {
        mLoginProvider.logout(callback)
    }
}