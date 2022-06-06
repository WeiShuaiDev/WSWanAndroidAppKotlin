package com.linwei.cams.module.login.ui.login

import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.getExtra
import com.linwei.cams.component.common.ktx.launch
import com.linwei.cams.component.common.ktx.startActivityForResult
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.module.login.ui.login.contract.ILoginView
import com.linwei.cams.module.login.ui.login.presenter.LoginPresenter
import com.linwei.cams.service.login.LoginRouterTable

@Route(path = LoginRouterTable.PATH_ACTIVITY_LOGIN)
class LoginActivity : MvpBaseActivity<ViewBinding, LoginPresenter>(), ILoginView {


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
}