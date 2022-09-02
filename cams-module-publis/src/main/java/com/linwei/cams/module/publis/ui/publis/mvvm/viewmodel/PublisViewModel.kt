package com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.publis.ui.publis.mvvm.model.PublisModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.model.PublisAuthorBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PublisViewModel @Inject constructor(private val publisModel: PublisModel) : MvvmViewModel() {

    private val _publicAuthorBeanList: MutableLiveData<List<PublisAuthorBean>> =
        MutableLiveData<List<PublisAuthorBean>>()
    val publicAuthorBeanList = _publicAuthorBeanList.asLiveData()

    private val _commonArticleBean: MutableLiveData<Page<CommonArticleBean>> =
        MutableLiveData<Page<CommonArticleBean>>()
    val commonArticleBean = _commonArticleBean.asLiveData()

    private val _refreshCollectStatus: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>()
    val refreshCollectStatus = _refreshCollectStatus.asLiveData()

    fun requestPublicAuthorData() {
        publisModel.fetchPublicAuthorData(object : ResponseCallback<List<PublisAuthorBean>> {

            override fun onSuccess(data: List<PublisAuthorBean>) {
                _publicAuthorBeanList.postValue(data)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }

    fun requestPublicArticleListData(
        page: Int,
        id: String?
    ) {
        val isRefresh = page == 0
        publisModel.fetchPublicArticleListData(
            page,
            id,
            object : ResponseCallback<Page<CommonArticleBean>> {

                override fun onSuccess(data: Page<CommonArticleBean>) {
                    _commonArticleBean.postValue(data)
                    _isRefresh.postValue(isRefresh)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    _showToast.postValue(errorMessage.message)
                    _isRefresh.postValue(isRefresh)
                }
            })
    }

    /**
     * 增加文章收藏
     */
    fun requestCollectStatus(id: Int) {
        publisModel.collectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _refreshCollectStatus.postValue(true)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }

    /**
     * 取消文章收藏
     */
    fun requestUnCollectStatus(id: Int) {
        publisModel.unCollectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _refreshCollectStatus.postValue(false)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }
}