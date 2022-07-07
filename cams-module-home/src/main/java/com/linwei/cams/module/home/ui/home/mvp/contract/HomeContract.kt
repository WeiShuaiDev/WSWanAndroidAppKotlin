package com.linwei.cams.module.home.ui.home.mvp.contract

import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.ArticleBean
import com.linwei.cams.service.home.model.BannerBean

interface IHomeModel : IMvpModel {

    fun fetchArticleData(
        page: Int,
        callback: ResponseCallback<Page<ArticleBean>>
    )

    fun fetchBannerData(
        callback: ResponseCallback<List<BannerBean>>
    )

    fun collectStatus(id: Int, callback: ResponseCallback<Any>)

    fun unCollectStatus(id: Int, callback: ResponseCallback<Any>)
}

interface IHomePresenter : IMvpPresenter {
    fun requestArticleData(page: Int)

    fun requestBannerData()

    fun requestCollectStatus(id: Int)

    fun requestUnCollectStatus(id: Int)
}

interface IHomeView : IMvpView {

    fun articleDataToView(page: Page<ArticleBean>)

    fun bannerDataToView(data: List<BannerBean>)

    fun refreshDataStatus(isRefresh:Boolean)

    fun refreshCollectStatus(status: Boolean)
}