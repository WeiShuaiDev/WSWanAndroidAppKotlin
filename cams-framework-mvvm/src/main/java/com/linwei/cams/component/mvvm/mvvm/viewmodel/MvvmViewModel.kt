package com.linwei.cams.component.mvvm.mvvm.viewmodel

import androidx.lifecycle.*
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.model.LoadingDialogEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构  `ViewModel` 接口定义
 *-----------------------------------------------------------------------
 */
open class MvvmViewModel : ViewModel(), DefaultLifecycleObserver {

    protected val _isRefresh: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val isRefresh = _isRefresh.asLiveData()

    protected val _showToast: MutableLiveData<String> =
        MutableLiveData<String>()
    val showToast = _showToast.asLiveData()

    protected val _showSnackBar: MutableLiveData<String> =
        MutableLiveData<String>()
    val showSnackBar = _showSnackBar.asLiveData()

    protected val _loadingDialog: MutableLiveData<LoadingDialogEvent> =
        MutableLiveData<LoadingDialogEvent>()
    val loadingDialog = _loadingDialog.asLiveData()

}