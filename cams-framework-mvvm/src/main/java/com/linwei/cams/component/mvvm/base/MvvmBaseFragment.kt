package com.linwei.cams.component.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.component.common.ktx.snackBar
import com.linwei.cams.component.common.utils.toast
import com.linwei.cams.component.mvvm.mvvm.ViewModelDelegate
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMDependant
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMProviderInterface
import com.quyunshuo.androidbaseframemvvm.base.utils.network.AutoRegisterNetListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkStateChangeListener
import com.quyunshuo.androidbaseframemvvm.base.utils.network.NetworkTypeEnum
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/2/24
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:  `MVVM` 架构 `Fragment` 核心基类
 *-----------------------------------------------------------------------
 */

abstract class MvvmBaseFragment<DB : ViewDataBinding, VM : MvvmViewModel> :
    CommonBaseFragment<ViewBinding>(), ViewModelDelegate<VM>, IMvvmView<VM>, VMDependant<VM>,
    NetworkStateChangeListener {

    @Inject
    lateinit var mVmProvider: VMProviderInterface

    protected var mViewModel: VM? = null

    protected var mDataBinding: DB? = null

    protected var mAutoRegisterNet: AutoRegisterNetListener? = null

    override fun dynamicFetchRootView(inflater: LayoutInflater, container: ViewGroup?): View? {
        return dataBinding(inflater)
    }

    override fun onViewCreatedExpand(view: View, savedInstanceState: Bundle?) {
        super.onViewCreatedExpand(view, savedInstanceState)
        initViewModel()
        initNetworkListener()
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = createViewModel()
        if (mViewModel == null) {
            mViewModel = mVmProvider.provideVM(this)
        }

        if (mViewModel != null) {
            lifecycle.addObserver(mViewModel!!)
        }

        bindViewModel(mViewModel, this)
    }

    /**
     * 初始化DataBinding
     */
    private fun dataBinding(inflater: LayoutInflater): View? {
        if (hasDataBinding()) {
            val rootLayoutId = getRootLayoutId()
            mDataBinding = DataBindingUtil.inflate(
                inflater,
                rootLayoutId, null, false
            )
            return mDataBinding!!.root
        }
        return null
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

    /**
     * 创建 `ViewModel` 类
     * @return [VM]
     */
    override fun createViewModel(): VM? = null

    override fun hasViewBinding(): Boolean = false

    override fun showSnackBar(message: String?) {
        activity?.window?.decorView?.snackBar(message)
    }

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

    override fun getVMClass(): KClass<VM> {
        return fetchVMClass().kotlin
    }

    /**
     * 销毁 'ViewModel'
     */
    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.let {
            lifecycle.removeObserver(it)
            mViewModel = null
        }

        mAutoRegisterNet?.let {
            lifecycle.removeObserver(it)
            mAutoRegisterNet = null
        }
    }
}