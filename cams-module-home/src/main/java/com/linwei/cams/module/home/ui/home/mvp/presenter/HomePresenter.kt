package com.linwei.cams.module.home.ui.home.mvp.presenter

import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeModel
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomePresenter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeView
import com.linwei.cams.module.home.ui.home.mvp.model.HomeModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.BannerBean

class HomePresenter(
    private var rootView: IHomeView?,
    private val model: IHomeModel = HomeModel()
) : MvpPresenter<IHomeView, IHomeModel>(rootView, model), IHomePresenter {

    /**
     * 获取文章数据
     */
    override fun requestArticleListData(page: Int) {
        rootView?.let {
            model.fetchArticleListData(page, object : ResponseCallback<Page<CommonArticleBean>> {
                override fun onSuccess(data: Page<CommonArticleBean>) {
                    it.commonArticleDataToView(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                    val isRefresh = page == 0
                    it.refreshDataStatus(isRefresh)
                }
            })
        }
    }

    /**
     * 获取轮播图数据
     */
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

    /**
     * 增加文章收藏
     */
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

    /**
     * 取消文章收藏
     */
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