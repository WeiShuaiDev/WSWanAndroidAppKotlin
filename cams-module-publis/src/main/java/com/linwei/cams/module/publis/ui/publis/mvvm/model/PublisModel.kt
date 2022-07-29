package com.linwei.cams.module.publis.ui.publis.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.publis.provider.PublisProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.model.PublisAuthorBean
import javax.inject.Inject

class PublisModel @Inject constructor(private val publisProvider: PublisProviderImpl) :
    MvvmModel() {

    fun fetchPublicAuthorData(callback: ResponseCallback<List<PublisAuthorBean>>) {
        publisProvider.fetchPublicAuthorData(callback)
    }

    fun fetchPublicArticleListData(
        page: Int,
        id: String?,
        callback: ResponseCallback<Page<CommonArticleBean>>
    ) {
        publisProvider.fetchPublicArticleListData(page, id, callback)
    }

}