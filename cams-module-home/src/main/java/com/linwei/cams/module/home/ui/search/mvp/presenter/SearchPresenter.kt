package com.linwei.cams.module.home.ui.search.mvp.presenter

import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.database.entity.SearchHistoryEntity
import com.linwei.cams.component.mvp.mvp.presenter.MvpPresenter
import com.linwei.cams.module.home.ui.search.mvp.contract.ISearchModel
import com.linwei.cams.module.home.ui.search.mvp.contract.ISearchPresenter
import com.linwei.cams.module.home.ui.search.mvp.contract.ISearchView
import com.linwei.cams.module.home.ui.search.mvp.model.SearchModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.SearchBean

class SearchPresenter(
    private var rootView: ISearchView?,
    private val model: ISearchModel = SearchModel()
) : MvpPresenter<ISearchView, ISearchModel>(rootView, model), ISearchPresenter {

    override fun requestHotSearchData() {
        rootView?.let {
            model.fetchHotSearchData(object : ResponseCallback<List<SearchBean.SearchDetailsBean>> {
                override fun onSuccess(data: List<SearchBean.SearchDetailsBean>) {
                    it.hotSearchDataToView(data)
                }

                override fun onFailed(errorMessage: ErrorMessage) {
                    it.showToast(errorMessage.message)
                }
            })
        }
    }

    override fun deleteAllLocalHis() {
        model.deleteAllLocalHis()
    }

    override fun deleteLocalHisById(id: Long) {
        model.deleteLocalHisById(id)
    }

    override fun selectLocalHis() {
        rootView?.let {
            val selectLocalHis = model.selectLocalHis()
        }
    }

    override fun saveLocalHis(his: String) {
        val searchHistoryList: List<SearchHistoryEntity?>? = model.selectLocalHis()
        searchHistoryList?.let {
            if (it.isNotNullOrSize()) {
                var id: Long? = 0
                for (entity in it) {
                    if (entity?.name == his) {
                        id = entity.id
                        break
                    }
                }
                if (id != 0L) {
                    model.deleteLocalHisById(id!!)
                }
                model.insertPerson(SearchHistoryEntity(his))
            }
        }
    }

    override fun insertPerson(entity: SearchHistoryEntity) {
        rootView?.let {
            val insertPerson = model.insertPerson(entity)
        }
    }
}