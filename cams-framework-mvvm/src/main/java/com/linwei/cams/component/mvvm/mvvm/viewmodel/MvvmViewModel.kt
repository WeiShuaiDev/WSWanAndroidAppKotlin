package com.linwei.cams.component.mvvm.mvvm.viewmodel

import androidx.lifecycle.*
import com.linwei.cams.component.mvvm.ktx.asLiveData

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

    private val _isRefresh: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val isRefresh = _isRefresh.asLiveData()

}