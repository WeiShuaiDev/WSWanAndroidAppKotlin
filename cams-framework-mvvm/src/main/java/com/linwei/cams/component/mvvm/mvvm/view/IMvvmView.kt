package com.linwei.cams.component.mvvm.mvvm.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.linwei.cams.component.common.ktx.app
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构  `View` 接口定义
 *-----------------------------------------------------------------------
 */
interface IMvvmView<VM : MvvmViewModel> {

    /**
     * 获取 [ViewModel] 对象
     * @return VM [ViewModel]
     */
    fun createViewModel(): VM?

    /**
     * 绑定 [ViewModel] 对象
     * @param VM [viewModel]
     * @param owner [LifecycleOwner]
     */
    fun bindViewModel(viewModel: VM?, owner: LifecycleOwner) {
        viewModel?.let {
            it.showToast.observe(owner) { message ->
                showToast(message)
            }

            it.showSnackBar.observe(owner) { message ->
                showSnackBar(message)
            }

            it.isRefresh.observe(owner) { event ->
                refreshDataStatus(event)
            }

            it.loadingDialog.observe(owner) { event ->
                takeIf { event.isShow }?.apply {
                    showLoadingDialog(event.message)
                } ?: run {
                    dismissLoadingDialog()
                }
            }
        }
        bindOtherMvvmViewEvent(viewModel, owner)
    }

    /**
     * 绑定其他事件对象
     * @param viewModel [VM]
     */
    fun bindOtherMvvmViewEvent(viewModel: VM?, owner: LifecycleOwner)

    /**
     * 显示 SnackBar
     * @param message [String]
     */
    fun showSnackBar(message: String?)

    /**
     * 显示Toast
     * @param message [String]
     */
    fun showToast(message: String?)

    /**
     * 显示加载框
     * @param message [String]
     */
    fun showLoadingDialog(message: String?)

    /**
     * 关闭加载框
     */
    fun dismissLoadingDialog()

    /**
     * 列表刷新状态
     */
    fun refreshDataStatus(isRefresh: Boolean)

}