package com.linwei.cams.module.login.ui.login.fragment

import android.os.Handler
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.ktx.isNullOrEmpty
import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.login.databinding.LoginFragmentAccountRegisterBinding
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginView
import com.linwei.cams.module.login.ui.login.mvp.presenter.LoginPresenter
import com.linwei.cams.service.base.model.UserInfoBean

class AccountRegisterFragment :
    MvpBaseFragment<LoginFragmentAccountRegisterBinding, LoginPresenter>(),
    ILoginView {

    override fun hasInjectARouter(): Boolean = true

    override fun getPresenter(): LoginPresenter = LoginPresenter(this)

    override fun initEvent() {
        mViewBinding?.loginCloseView?.setOnClickListener {
            activity?.finish()
        }


        mViewBinding?.loginLoginView?.setOnClickListener {
            val userName = mViewBinding?.loginInputAccountView?.text.toString()
            if (userName.isNullOrEmpty()) {
                showToast("账号为空！")
                return@setOnClickListener
            }
            val password = mViewBinding?.loginInputPasswordView?.text.toString()
            if (password.isNullOrEmpty()) {
                showToast("密码为空！")
                return@setOnClickListener
            }
            mViewBinding?.loginLoginView?.startAnim()
            Handler().postDelayed(
                { mMvpPresenter?.requestRegister(userName, password, password) },
                2000
            )

        }
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun refreshRegisterStatusToView(status: Boolean, userInfo: UserInfoBean?) {
        if (status) {
            userInfo?.let {
                AppDataMMkvProvided().saveUserInfo(it)
            }
            activity?.finish()
        }
    }

    override fun onStop() {
        super.onStop()
        mViewBinding?.loginLoginView?.reset()
    }
}