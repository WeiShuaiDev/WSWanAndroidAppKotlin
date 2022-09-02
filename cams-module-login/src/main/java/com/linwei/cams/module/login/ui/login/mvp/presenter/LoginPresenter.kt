package com.linwei.cams.module.login.ui.login.mvp.presenter

import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginModel
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginPresenter
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginView
import com.linwei.cams.module.login.ui.login.mvp.model.LoginModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.UserInfoBean

class LoginPresenter(
    private var rootView: ILoginView?,
    private val model: ILoginModel = LoginModel()
) : MvpPresenter<ILoginView, ILoginModel>(rootView, model), ILoginPresenter {

    override fun requestLogin(userName: String, passWord: String) {
        rootView?.let {
            model.login(userName, passWord, object : ResponseCallback<UserInfoBean> {
                override fun onSuccess(data: UserInfoBean) {
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestRegister(userName: String, passWord: String, rePassWord: String) {
        rootView?.let {
            model.register(userName, passWord, rePassWord, object : ResponseCallback<UserInfoBean> {
                override fun onSuccess(data: UserInfoBean) {
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestLogout() {
        rootView?.let {
            model.logout(object : ResponseCallback<Any> {

                override fun onSuccess(data: Any) {

                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

}