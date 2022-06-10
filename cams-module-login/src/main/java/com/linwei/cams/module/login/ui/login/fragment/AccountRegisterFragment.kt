package com.linwei.cams.module.login.ui.login.fragment

import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.login.databinding.LoginFragmentAccountRegisterBinding
import com.linwei.cams.module.login.ui.login.contract.ILoginView
import com.linwei.cams.module.login.ui.login.presenter.LoginPresenter
import com.linwei.cams.service.login.model.UserInfo

class AccountRegisterFragment : MvpBaseFragment<LoginFragmentAccountRegisterBinding, LoginPresenter>(),
    ILoginView {

    override fun hasInjectARouter(): Boolean = true

    override fun getPresenter(): LoginPresenter = LoginPresenter(this)

    override fun initEvent() {

    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun refreshLoginStatusToView(status: Boolean, userInfo: UserInfo) {

    }

    override fun refreshLogoutStatus(status: Boolean) {

    }
}