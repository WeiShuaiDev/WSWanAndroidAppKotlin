package com.linwei.cams.module.mine.ui.mycollect.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.ui.mycollect.mvvm.model.MyCollectModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyCollectViewModel @Inject constructor(private val myCollectModel: MyCollectModel) :
    MvvmViewModel() {

    private val _commonArticleBeanPage: MutableLiveData<Page<CommonArticleBean>> =
        MutableLiveData<Page<CommonArticleBean>>()
    val commonArticleBeanPage = _commonArticleBeanPage.asLiveData()

    private val _unCollectStatus: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val unCollectStatus = _unCollectStatus.asLiveData()

    fun requestListMyCollectData(page: Int) {
        myCollectModel.fetchListMyCollectData(
            page,
            object : ResponseCallback<Page<CommonArticleBean>> {

                override fun onSuccess(data: Page<CommonArticleBean>) {
                    data.datas?.map { it.collect = true }
                    _commonArticleBeanPage.postValue(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    _showToast.postValue(errorMessage.message)
                    val isRefresh = page == 0
                    _isRefresh.postValue(isRefresh)
                }
            })
    }

    fun requestUnCollectStatus(id: Int) {
        myCollectModel.unCollectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _unCollectStatus.postValue(true)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _unCollectStatus.postValue(false)
                _showToast.postValue(errorMessage.message)
            }
        })
    }

}