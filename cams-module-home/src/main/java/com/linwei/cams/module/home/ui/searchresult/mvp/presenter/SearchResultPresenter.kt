package com.linwei.cams.module.home.ui.searchresult.mvp.presenter

import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.module.home.ui.searchresult.mvp.contract.ISearchResultModel
import com.linwei.cams.module.home.ui.searchresult.mvp.contract.ISearchResultPresenter
import com.linwei.cams.module.home.ui.searchresult.mvp.contract.ISearchResultView
import com.linwei.cams.module.home.ui.searchresult.mvp.model.SearchResultModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page

class SearchResultPresenter(
    private var rootView: ISearchResultView?,
    private val model: ISearchResultModel = SearchResultModel()
) : MvpPresenter<ISearchResultView, ISearchResultModel>(rootView, model), ISearchResultPresenter {


    override fun requestSearchListData(page: Int, keyword: String?) {
        rootView?.let {
            model.fetchArticleListData(
                page,
                keyword,
                object : ResponseCallback<Page<CommonArticleBean>> {
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
            model.collectStatus(id, object : ResponseCallback<Any> {
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