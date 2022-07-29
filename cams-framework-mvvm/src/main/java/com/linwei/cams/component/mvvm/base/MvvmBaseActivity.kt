package com.linwei.cams.component.mvvm.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.component.mvvm.mvvm.ViewModelDelegate
import com.linwei.cams.component.mvvm.mvvm.view.MvvmView
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMDependant
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMProviderInterface
import com.quyunshuo.androidbaseframemvvm.base.utils.network.AutoRegisterNetListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateChangeListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkTypeEnum
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVVM` 架构 `Activity` 核心基类
 *-----------------------------------------------------------------------
 */

abstract class MvvmBaseActivity<DB : ViewDataBinding, VM : MvvmViewModel> :
    CommonBaseActivity<ViewBinding>(), ViewModelDelegate<VM>, MvvmView<VM>, VMDependant<VM>,
    NetworkStateChangeListener {

    @Inject
    lateinit var mVmProvider: VMProviderInterface

    protected var mViewModel: VM? = null

    protected lateinit var mDataBinding: DB

    protected var mAutoRegisterNet: AutoRegisterNetListener? = null

    override fun onCreateExpand() {
        super.onCreateExpand()
        initViewModel()
        dataBinding()
        initNetworkListener()
    }

    /**
     * 初始化ViewModel
     */
    protected fun initViewModel() {
        mViewModel = createViewModel()
        if (mViewModel == null) {
            mViewModel = mVmProvider.provideVM(this)
        }

        if (mViewModel != null) {
            lifecycle.addObserver(mViewModel!!)
        }
    }

    /**
     * 初始化DataBinding
     */
    protected fun dataBinding() {
        if (hasDataBinding()) {
            val rootLayoutId = getRootLayoutId()
            mDataBinding = DataBindingUtil.setContentView<DB>(this, rootLayoutId).apply {
                lifecycleOwner=this@MvvmBaseActivity
            }
            return
        }
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
     * 获取 [ViewModelProvider.Factory] 对象
     * @return mViewModelFactory [ViewModelProvider.Factory]
     */
    protected fun getViewModelFactory(): ViewModelProvider.Factory = mViewModelFactory

    protected open fun hasDataBinding(): Boolean = true

    override fun createViewModel(): VM? = null

    override fun hasViewBinding(): Boolean = false

    override fun showSnackBar(message: String) = window.decorView.snackBar(message)

    override fun showLoadingDialog(message: String) {
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

    override fun getVMClass(): KClass<VM> {
        return fetchVMClass().kotlin
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