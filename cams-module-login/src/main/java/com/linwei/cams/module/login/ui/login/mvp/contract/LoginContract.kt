package com.linwei.cams.module.login.ui.login.mvp.contract

import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.UserInfoBean

interface ILoginModel : IMvpModel {

    fun login(
        userName: String,
        passWord: String,
        callback: ResponseCallback<UserInfoBean>
    )

    fun register(
        userName: String,
        passWord: String,
        rePassWord: String,
        callback: ResponseCallback<UserInfoBean>
    )

    fun logout(
        callback: ResponseCallback<Any>
    )
}

interface ILoginPresenter : IMvpPresenter {
    fun requestLogin(userName: String, passWord: String)

    fun requestRegister(userName: String, passWord: String, rePassWord: String)

    fun requestLogout()
}

interface ILoginView : IMvpView {

    fun refreshLoginStatusToView(status: Boolean, userInfo: UserInfoBean?){}

    fun refreshRegisterStatusToView(status: Boolean, userInfo: UserInfoBean?){}

    fun refreshLogoutStatus(status: Boolean){}
}