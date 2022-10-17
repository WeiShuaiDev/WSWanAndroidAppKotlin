package com.linwei.cams.module.login.ui.login

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.login.databinding.LoginActivityLoginBinding
import com.linwei.cams.service.login.LoginRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = LoginRouterTable.PATH_ACTIVITY_LOGIN)
class LoginActivity : CommonBaseActivity<LoginActivityLoginBinding>() {

    @Autowired
    lateinit var title: String

    override fun hasInjectARouter(): Boolean = true


    override fun initEvent() {

    }

    override fun initData() {

    }

    override fun initView() {

    }
}