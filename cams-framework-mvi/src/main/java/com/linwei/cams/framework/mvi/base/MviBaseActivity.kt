package com.linwei.cams.framework.mvi.base

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.view.ContextThemeWrapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.R
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.LanguageUtils
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.framework.mvi.mvi.ViewModelDelegate
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.view.IMviView
import com.quyunshuo.androidbaseframemvvm.base.utils.network.AutoRegisterNetListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateChangeListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkTypeEnum
import kotlin.reflect.KClass

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVI` 架构 `Activity` 核心基类
 *-----------------------------------------------------------------------
 */
abstract class MviBaseActivity<VB : ViewBinding, VM : MviViewModel> : CommonBaseActivity<VB>(),
    ViewModelDelegate<VM>, IMviView<VM>, NetworkStateChangeListener {

    protected var mViewModel: VM? = null

    protected var mAutoRegisterNet: AutoRegisterNetListener? = null

    override fun onCreateExpand() {
        super.onCreateExpand()
        initViewModel()
        initNetworkListener()
    }

    /**
     * 初始化ViewModel
     */
    protected fun initViewModel() {
        mViewModel = createViewModel()
        if (mViewModel == null) {
            mViewModel = obtainViewModel(fetchVMClass())
        }

        if (mViewModel != null) {
            lifecycle.addObserver(mViewModel!!)
        }
        bindViewModel(mViewModel, this)
    }

    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        if (mAutoRegisterNet == null) {
            mAutoRegisterNet = AutoRegisterNetListener(this)
        }
        lifecycle.addObserver(mAutoRegisterNet!!)
    }

    /**
     * 根据 `ViewModel` 的 [vmClass],获取 `ViewModel` 对象
     * @param vmClass [Class]
     * @return  [ViewModel]
     */
    private fun obtainViewModel(vmClass: Class<VM>): VM = obtainViewModel(viewModelStore, vmClass)

    /**
     * 根据 `ViewModel` 的 [vmClass],获取 `ViewModel` 对象
     * @param store [ViewModelStore]
     * @param vmClass [Class]
     * @return  [ViewModel]
     */
    private fun obtainViewModel(store: ViewModelStore, vmClass: Class<VM>): VM =
        createViewModelProvider(store, vmClass.kotlin).value

    /**
     * 创建 [ViewModelProvider] 对象
     * @param store [ViewModelStore]
     * @return [ViewModelProvider] 对象
     */
    private fun createViewModelProvider(store: ViewModelStore, vmClass: KClass<VM>):Lazy<VM> {
        val factoryPromise = { defaultViewModelProviderFactory }
        return ViewModelLazy(vmClass, { store }, factoryPromise)
    }

    /**
     * 获取 [ViewModelProvider.Factory] 对象
     * @return mViewModelFactory [ViewModelProvider.Factory]
     */
    protected fun getViewModelFactory(): ViewModelProvider.Factory = mViewModelFactory

    override fun createViewModel(): VM? = null

    override fun showSnackBar(message: String?) = window.decorView.snackBar(message)

    override fun showLoadingDialog(message: String?) {
    }

    override fun dismissLoadingDialog() {
    }

    override fun showToast(message: String?) = toast(message)

    override fun networkConnectChange(isConnected: Boolean) {
        if (!isConnected) {
            toast("网络出现问题~~")
        }
    }

    override fun networkTypeChange(type: NetworkTypeEnum) {
    }

    /**
     *  获取 `ViewModel` 对象
     *  @return mViewModel [VM]
     */
    protected fun getViewModel(): VM? = mViewModel

    protected open fun shouldSupportMultiLanguage(): Boolean = true

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

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.let {
            lifecycle.removeObserver(mViewModel!!)
            mViewModel = null
        }

        mAutoRegisterNet?.let {
            lifecycle.removeObserver(mAutoRegisterNet!!)
            mAutoRegisterNet = null
        }
    }
}