package com.linwei.cams.module.login.ui.login

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.module.login.databinding.LoginActivityLoginBinding
import com.linwei.cams.module.login.ui.login.contract.ILoginView
import com.linwei.cams.module.login.ui.login.presenter.LoginPresenter
import com.linwei.cams.service.login.LoginRouterTable
import com.linwei.cams.service.login.model.UserInfo

@Route(path = LoginRouterTable.PATH_ACTIVITY_LOGIN)
class LoginActivity : MvpBaseActivity<LoginActivityLoginBinding, LoginPresenter>(), ILoginView {

    @Autowired
    lateinit var title: String

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