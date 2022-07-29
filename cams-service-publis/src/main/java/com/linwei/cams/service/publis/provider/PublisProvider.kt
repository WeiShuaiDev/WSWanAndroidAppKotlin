package com.linwei.cams.service.publis.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.model.PublisAuthorBean

interface PublisProvider : IProvider {

    fun fetchPublicAuthorData(callback: ResponseCallback<List<PublisAuthorBean>>)

    fun fetchPublicArticleListData(
        page: Int,
        id: String?,
        callback: ResponseCallback<Page<CommonArticleBean>>
    )
}