package com.linwei.cams.module.home.ui.home.mvp.model

import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.home.provider.HomeProviderImpl
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean
import javax.inject.Inject

class HomeModel : MvpModel(), IHomeModel {

    @Inject
    lateinit var mHomeProvider: HomeProviderImpl

    override fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    ) {
        mHomeProvider.fetchHomeData(page, callback)
    }

    override fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>) {
        mHomeProvider.fetchBannerData(callback)
    }

}