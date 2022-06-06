package com.linwei.cams.module.login.ui.login.contract

import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean
import com.linwei.cams.service.login.model.UserInfo

interface ILoginModel : IMvpModel {

    fun login(
        userName: String,
        passWord: String,
        callback: ResponseCallback<UserInfo>
    )

    fun register(
        userName: String,
        passWord: String,
        rePassWord: String,
        callback: ResponseCallback<UserInfo>
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

}