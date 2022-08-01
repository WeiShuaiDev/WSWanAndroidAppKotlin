package com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.common.ktx.isNotNullOrSize
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

    private val _commonArticleBean: MutableLiveData<List<CommonArticleBean>> =
        MutableLiveData<List<CommonArticleBean>>()
    val commonArticleBean = _commonArticleBean.asLiveData()

    fun requestPublicAuthorData() {
        publisModel.fetchPublicAuthorData(object : ResponseCallback<List<PublisAuthorBean>> {

            override fun onSuccess(data: List<PublisAuthorBean>) {
                _publicAuthorBeanList.postValue(data)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _publicAuthorBeanList.postValue(mutableListOf())
            }
        })
    }

    fun requestPublicArticleListData(
        page: Int,
        id: String?
    ) {
        publisModel.fetchPublicArticleListData(
            page,
            id,
            object : ResponseCallback<Page<CommonArticleBean>> {

                override fun onSuccess(data: Page<CommonArticleBean>) {
                    if(data.datas.isNotNullOrSize()) {
                        _commonArticleBean.postValue(data.datas!!)
                        System.out.println("CommonArticleBean"+data.toString())
                    }
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    _commonArticleBean.postValue(mutableListOf())
                }
            })
    }
}