package com.linwei.cams.service.home.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.SearchBean

interface HomeProvider : IProvider {

    fun fetchArticleListData(page: Int, callback: ResponseCallback<Page<CommonArticleBean>>)

    fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>)

    fun fetchHotSearchData(callback: ResponseCallback<List<SearchBean.SearchDetailsBean>>)

    fun fetchSearchData(page: Int,
                        keyword: String?,callback:ResponseCallback<Page<CommonArticleBean>>)

}