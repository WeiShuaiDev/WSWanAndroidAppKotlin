package com.linwei.cams.service.home.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.ArticleEntity
import com.linwei.cams.service.home.model.BannerEntity

interface HomeProvider : IProvider {

    fun fetchArticleData(page: Int, callback: ResponseCallback<Page<ArticleEntity>>)

    fun fetchBannerData(callback: ResponseCallback<List<BannerEntity>>)

}