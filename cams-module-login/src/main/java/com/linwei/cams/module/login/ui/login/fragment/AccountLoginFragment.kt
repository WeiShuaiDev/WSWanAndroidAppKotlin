package com.linwei.cams.module.login.ui.login.fragment

import android.os.Handler
import androidx.navigation.fragment.NavHostFragment
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.ktx.isNullOrEmpty
import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.login.R
import com.linwei.cams.module.login.databinding.LoginFragmentAccountLoginBinding
import com.linwei.cams.module.login.ui.login.mvp.contract.ILoginView
import com.linwei.cams.module.login.ui.login.mvp.presenter.LoginPresenter
import com.linwei.cams.service.base.model.UserInfoBean

class AccountLoginFragment : MvpBaseFragment<LoginFragmentAccountLoginBinding, LoginPresenter>(),
    ILoginView {

    override fun hasInjectARouter(): Boolean = true

    override fun getPresenter(): LoginPresenter = LoginPresenter(this)

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {
        mViewBinding?.loginCloseView?.setOnClickListener {
            activity?.finish()
        }

        mViewBinding?.loginRegisterView?.setOnClickListener {
            NavHostFragment.findNavController(this@AccountLoginFragment)
                .navigate(R.id.action_fragment_register)
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
            Handler().postDelayed({ mMvpPresenter?.requestLogin(userName, password) }, 2000)

        }
    }

    override fun refreshLoginStatusToView(status: Boolean, userInfo: UserInfoBean?) {
        if (status) {
            userInfo?.let {
                AppDataMMkvProvided().saveUserInfo(it)
            }
            mViewBinding?.loginLoginView?.success()
            activity?.finish()
        } else {
            mViewBinding?.loginLoginView?.reset()
        }
    }

    override fun onStop() {
        super.onStop()
        mViewBinding?.loginLoginView?.reset()
    }

}