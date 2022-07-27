package com.linwei.cams.module.home.ui.home.mvp.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.home.provider.HomeProviderImpl
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.mine.provider.MineProviderHelper

class HomeModel : MvpModel(), IHomeModel {

    private val mHomeProvider: HomeProviderImpl = HomeProviderImpl()

    private val mMineProvider = MineProviderHelper.getMineProvider()

    override fun fetchArticleData(page: Int, callback: ResponseCallback<Page<CommonArticleBean>>) {
        mHomeProvider.fetchArticleData(page, callback)
    }

    override fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>) {
        mHomeProvider.fetchBannerData(callback)
    }

    override fun collectStatus(id: Int, callback: ResponseCallback<Any>) {
        mMineProvider?.collectStatus(id, callback)
    }

    override fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        mMineProvider?.unCollectStatus(id, callback)
    }

}