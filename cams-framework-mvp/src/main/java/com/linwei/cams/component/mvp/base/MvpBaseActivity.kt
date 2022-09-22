package com.linwei.cams.component.mvp.base

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.R
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.LanguageUtils
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateChangeListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateClient
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkTypeEnum

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构 基类MvpBaseActivity
 * -----------------------------------------------------------------------
 */
abstract class MvpBaseActivity<VB : ViewBinding, P : IMvpPresenter> :
    CommonBaseActivity<VB>(), IMvpView, NetworkStateChangeListener {

    protected var mMvpPresenter: P? = null

    protected abstract fun getPresenter(): P?

    override fun onCreateExpand() {
        super.onCreateExpand()
        mMvpPresenter=getPresenter()

    }

    override fun showLoadingDialog(message: String?) {

    }

    override fun showSnackBar(message: String?)=window.decorView.snackBar(message)

    override fun showToast(message: String?) {
        toast(message)
    }

    override fun networkConnectChange(isConnected: Boolean) {
        if(!isConnected){
            toast("网络出现问题~~")
        }
    }

    override fun networkTypeChange(type: NetworkTypeEnum) {
    }

    override fun dismissLoadingDialog() {

    }

    override fun attachBaseContext(newBase: Context?) {
        if (shouldSupportMultiLanguage()) {
            val context: Context? = LanguageUtils.attachBaseContext(newBase){
                AppDataMMkvProvided().getLanguage()
            }
            val configuration = context?.resources?.configuration
            // 此处的ContextThemeWrapper是androidx.appcompat.view包下的
            // 你也可以使用android.view.ContextThemeWrapper，但是使用该对象最低只兼容到API 17
            // 所以使用 androidx.appcompat.view.ContextThemeWrapper省心
            val wrappedContext: ContextThemeWrapper = object : ContextThemeWrapper(
                context,
                R.style.Theme_AppCompat_Empty
            ) {
                override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
                    overrideConfiguration?.setTo(configuration)
                    super.applyOverrideConfiguration(overrideConfiguration)
                }
            }
            super.attachBaseContext(wrappedContext)
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onResume() {
        super.onResume()
        NetworkStateClient.setListener(this)
    }

    override fun onPause() {
        super.onPause()
        NetworkStateClient.removeListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpPresenter?.onDestroy()
        NetworkStateClient.removeListener()
    }
}