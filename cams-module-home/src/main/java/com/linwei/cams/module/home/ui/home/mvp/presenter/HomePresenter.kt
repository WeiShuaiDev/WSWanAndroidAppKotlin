package com.linwei.cams.module.home.ui.home.mvp.presenter

import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeModel
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomePresenter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeView
import com.linwei.cams.module.home.ui.home.mvp.model.HomeModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean

class HomePresenter(
    private var rootView: IHomeView?,
    private val model: IHomeModel = HomeModel()
) : MvpPresenter<IHomeView, IHomeModel>(rootView, model), IHomePresenter {

    override fun requestHomeData(page: Int) {
        rootView?.let {
            model.fetchHomeData(page, object : ResponseCallback<HomeBean> {
                override fun onSuccess(data: HomeBean) {
                    it.updateHomeDataToView(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestBannerData() {
        rootView?.let {
            model.fetchBannerData(object : ResponseCallback<List<BannerBean>> {
                override fun onSuccess(data: List<BannerBean>) {
                    it.updateBannerDataToView(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestCollectStatus(id: String) {
        rootView?.let {
            model.collectStatus(id, object : ResponseCallback<Any> {
                override fun onSuccess(data: Any) {
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun requestUnCollectStatus(id: String) {
        rootView?.let {
            model.unCollectStatus(id, object : ResponseCallback<Any> {
                override fun onSuccess(data: Any) {
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }
}