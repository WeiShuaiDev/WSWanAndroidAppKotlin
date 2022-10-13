package com.linwei.cams.module.home.ui.search.mvp.contract

import com.linwei.cams.component.database.entity.SearchHistoryEntity
import com.linwei.cams.component.mvp.mvp.model.IMvpModel
import com.linwei.cams.component.mvp.mvp.presenter.IMvpPresenter
import com.linwei.cams.component.mvp.mvp.view.IMvpView
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.SearchBean

interface ISearchModel : IMvpModel {

    fun fetchHotSearchData(
        callback: ResponseCallback<List<SearchBean.SearchDetailsBean>>
    )

    fun deleteAllLocalHis()

    fun deleteLocalHisById(id: Long)

    fun selectLocalHis():List<SearchHistoryEntity>?

    fun insertPerson(entity: SearchHistoryEntity):Long?
}

interface ISearchPresenter : IMvpPresenter {
    fun requestHotSearchData()

    fun deleteAllLocalHis()

    fun deleteLocalHisById(id: Long)

    fun selectLocalHis()

    fun saveLocalHis(his:String)

    fun insertPerson(entity: SearchHistoryEntity)

}

interface ISearchView : IMvpView {

    fun hotSearchDataToView(list: List<SearchBean.SearchDetailsBean>)

    fun selectLocalHisDataToView(list:List<SearchHistoryEntity>?)

}