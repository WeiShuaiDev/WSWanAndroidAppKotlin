package com.linwei.cams.module.home.ui.search

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.module.home.databinding.HomeActivitySearchBinding
import com.linwei.cams.module.home.ui.search.mvp.contract.ISearchView
import com.linwei.cams.module.home.ui.search.mvp.presenter.SearchPresenter
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.SearchBean
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = HomeRouterTable.PATH_ACTIVITY_SEARCH)
class SearchActivity : MvpBaseActivity<HomeActivitySearchBinding, SearchPresenter>(), ISearchView {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }

    override fun hotSearchDataToView(list: List<SearchBean.SearchDetailsBean>) {

    }

    override fun getPresenter(): SearchPresenter = SearchPresenter(this)

}