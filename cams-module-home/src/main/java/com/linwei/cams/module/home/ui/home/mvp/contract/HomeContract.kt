package com.linwei.cams.module.home.ui.home.mvp.contract

import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean

interface IHomeModel : IMvpModel {

    fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    )

    fun fetchBannerData(
        callback: ResponseCallback<List<BannerBean>>
    )

    fun collectStatus(id: String, callback: ResponseCallback<Any>)

    fun unCollectStatus(id: String, callback: ResponseCallback<Any>)
}

interface IHomePresenter : IMvpPresenter {
    fun requestHomeData(page: Int)

    fun requestBannerData()

    fun requestCollectStatus(id: String)

    fun requestUnCollectStatus(id: String)
}

interface IHomeView : IMvpView {

    fun updateHomeDataToView(homeBean: HomeBean)

    fun updateBannerDataToView(bannerList: List<BannerBean>)
}