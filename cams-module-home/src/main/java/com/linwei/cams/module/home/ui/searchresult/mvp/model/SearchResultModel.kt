package com.linwei.cams.module.home.ui.searchresult.mvp.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.home.provider.HomeProviderImpl
import com.linwei.cams.module.home.ui.searchresult.mvp.contract.ISearchResultModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.provider.MineProviderHelper

class SearchResultModel : MvpModel(), ISearchResultModel {

    private val mHomeProvider: HomeProviderImpl = HomeProviderImpl()

    private val mMineProvider = MineProviderHelper.getMineProvider()

    override fun fetchArticleListData(
        page: Int,
        keyword: String?,
        callback: ResponseCallback<Page<CommonArticleBean>>
    ) {
        mHomeProvider.fetchSearchData(page, keyword, callback)
    }

    override fun collectStatus(id: Int, callback: ResponseCallback<Any>) {
        mMineProvider?.collectStatus(id, callback)
    }

    override fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        mMineProvider?.unCollectStatus(id, callback)
    }


}