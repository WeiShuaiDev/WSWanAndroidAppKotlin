package com.linwei.cams.module.mine.ui.myshare.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.ui.myshare.mvvm.model.MyShareModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.mine.model.MyShareBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyShareViewModel @Inject constructor(private val myShareModel: MyShareModel) :
    MvvmViewModel() {

    private val _myShareBean: MutableLiveData<MyShareBean> =
        MutableLiveData<MyShareBean>()
    val myShareBean = _myShareBean.asLiveData()

    private val _collectStatus: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val collectStatus = _collectStatus.asLiveData()

    private val _deleteArticleStatus: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val deleteArticleStatus = _deleteArticleStatus.asLiveData()

    fun requestListMyShareData(page: Int) {
        myShareModel.fetchListMyShareData(page, object : ResponseCallback<MyShareBean> {

            override fun onSuccess(data: MyShareBean) {
                _myShareBean.postValue(data)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
                val isRefresh = page == 0
                _isRefresh.postValue(isRefresh)
            }
        })
    }

    fun requestDeleteArticle(id: Int) {
        myShareModel.deleteArticle(id, object : ResponseCallback<Any> {

            override fun onSuccess(data: Any) {
                _deleteArticleStatus.postValue(true)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
                _deleteArticleStatus.postValue(false)
            }
        })
    }

    fun requestUnCollectStatus(id: Int) {
        myShareModel.unCollectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _collectStatus.postValue(false)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }

    fun requestCollectStatus(id: Int) {
        myShareModel.collectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _collectStatus.postValue(true)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }
}