package com.linwei.cams.module.login.ui.login.fragment

import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.login.databinding.LoginFragmentAccountRegisterBinding
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginView
import com.linwei.cams.module.login.ui.login.mvp.presenter.LoginPresenter
import com.linwei.cams.service.base.model.UserInfoBean

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

    override fun refreshLoginStatusToView(status: Boolean, userInfo: UserInfoBean) {

    }

    override fun refreshLogoutStatus(status: Boolean) {

    }
}