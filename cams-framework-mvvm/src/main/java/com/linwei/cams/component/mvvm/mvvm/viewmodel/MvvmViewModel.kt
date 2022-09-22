package com.linwei.cams.component.mvvm.mvvm.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.model.LoadingDialogEvent
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2022/3/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构  `ViewModel` 接口定义
 *-----------------------------------------------------------------------
 */
open class MvvmViewModel : ViewModel(), DefaultLifecycleObserver , Consumer<Disposable> {

    private var mCompositeDisposable: CompositeDisposable? = null

     var title: ObservableField<String> =
        ObservableField<String>()

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

    protected open fun addDisposable(disposable: Disposable?) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        disposable?.let {
            mCompositeDisposable!!.add(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (mCompositeDisposable != null && !mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable!!.clear()
        }
    }

    override fun accept(disposable: Disposable?) {
        addDisposable(disposable)
    }

}