package com.linwei.cams.module.home.ui.search.mvp.model

import com.linwei.cams.component.database.entity.SearchHistoryEntity
import com.linwei.cams.component.database.searchHistoryDao
import com.linwei.cams.component.mvp.mvp.model.MvpModel
import com.linwei.cams.module.home.provider.HomeProviderImpl
import com.linwei.cams.module.home.ui.search.mvp.contract.ISearchModel
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.SearchBean

class SearchModel : MvpModel(), ISearchModel {

    private val mHomeProvider: HomeProviderImpl = HomeProviderImpl()

    override fun fetchHotSearchData(callback: ResponseCallback<List<SearchBean.SearchDetailsBean>>) {
        mHomeProvider.fetchHotSearchData(callback)
    }

    override fun deleteAllLocalHis() {
        searchHistoryDao?.deleteAll()
    }

    override fun deleteLocalHisById(id: Long) {
        searchHistoryDao?.deleteById(id)
    }

    override fun selectLocalHis(): List<SearchHistoryEntity>? = searchHistoryDao?.selectHis()

    override fun insertPerson(entity: SearchHistoryEntity): Long? =
        searchHistoryDao?.insertPerson(entity)


}