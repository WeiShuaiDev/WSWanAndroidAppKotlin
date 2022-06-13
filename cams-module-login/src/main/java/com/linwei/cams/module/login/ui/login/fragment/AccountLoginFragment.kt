package com.linwei.cams.module.login.ui.login.fragment

import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.login.databinding.LoginFragmentAccountLoginBinding
import com.linwei.cams.module.login.ui.login.contract.ILoginView
import com.linwei.cams.module.login.ui.login.presenter.LoginPresenter
import com.linwei.cams.service.login.model.UserInfoBean

class AccountLoginFragment : MvpBaseFragment<LoginFragmentAccountLoginBinding, LoginPresenter>(),
    ILoginView {

    override fun hasInjectARouter(): Boolean = true

    override fun getPresenter(): LoginPresenter = LoginPresenter(this)

    override fun initEvent() {

    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun refreshLoginStatusToView(status: Boolean, userInfo: UserInfoBean) {

    }

    override fun refreshLogoutStatus(status: Boolean) {

    }
}