package com.linwei.cams.module.home.ui.home.mvp.presenter

import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeModel
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomePresenter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeView
import com.linwei.cams.module.home.ui.home.mvp.model.HomeModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.ArticleBean
import com.linwei.cams.service.home.model.BannerBean

class HomePresenter(
    private var rootView: IHomeView?,
    private val model: IHomeModel = HomeModel()
) : MvpPresenter<IHomeView, IHomeModel>(rootView, model), IHomePresenter {

    override fun requestArticleData(page: Int) {
        rootView?.let {
            model.fetchArticleData(page, object : ResponseCallback<Page<ArticleBean>> {
                override fun onSuccess(data: Page<ArticleBean>) {
                    it.articleDataToView(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                    it.refreshDataStatus(page == 0)
                }
            })
        }
    }

    override fun requestBannerData() {
        rootView?.let {
            model.fetchBannerData(object : ResponseCallback<List<BannerBean>> {
                override fun onSuccess(data: List<BannerBean>) {
                    it.bannerDataToView(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestCollectStatus(id: Int) {
        rootView?.let {
            model.collectStatus(id, object : ResponseCallback<Any> {
                override fun onSuccess(data: Any) {
                    it.refreshCollectStatus(true)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestUnCollectStatus(id: Int) {
        rootView?.let {
            model.unCollectStatus(id, object : ResponseCallback<Any> {
                override fun onSuccess(data: Any) {
                    it.refreshCollectStatus(false)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }
}