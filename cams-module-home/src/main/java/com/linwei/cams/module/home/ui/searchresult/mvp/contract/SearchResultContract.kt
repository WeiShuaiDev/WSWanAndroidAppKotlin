package com.linwei.cams.module.home.ui.searchresult.mvp.contract

import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page

interface ISearchResultModel : IMvpModel {
    fun fetchArticleListData(
        page: Int,
        keyword:String?,
        callback: ResponseCallback<Page<CommonArticleBean>>
    )

    fun collectStatus(id: Int, callback: ResponseCallback<Any>)

    fun unCollectStatus(id: Int, callback: ResponseCallback<Any>)
}

interface ISearchResultPresenter : IMvpPresenter {
    fun requestSearchListData(page: Int,keyword: String?)

    fun requestCollectStatus(id: Int)

    fun requestUnCollectStatus(id: Int)
}

interface ISearchResultView : IMvpView {

    fun commonArticleDataToView(page: Page<CommonArticleBean>)

    fun refreshDataStatus(isRefresh:Boolean)

    fun refreshCollectStatus(status: Boolean)

}