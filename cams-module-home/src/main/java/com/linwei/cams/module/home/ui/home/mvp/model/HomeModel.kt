package com.linwei.cams.module.home.ui.home.mvp.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.home.provider.HomeProviderImpl
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean
import com.linwei.cams.service.mine.provider.MineProviderHelper
import javax.inject.Inject

class HomeModel : MvpModel(), IHomeModel {

    @Inject
    lateinit var mHomeProvider: HomeProviderImpl

    private var mMineProvider = MineProviderHelper.getMineProvider()

    override fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    ) {
        mHomeProvider.fetchHomeData(page, callback)
    }

    override fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>) {
        mHomeProvider.fetchBannerData(callback)
    }

    override fun collectStatus(id: String, callback: ResponseCallback<Any>) {
        mMineProvider.collectStatus(id, callback)
    }

    override fun unCollectStatus(id: String, callback: ResponseCallback<Any>) {
        mMineProvider.unCollectStatus(id, callback)
    }

}