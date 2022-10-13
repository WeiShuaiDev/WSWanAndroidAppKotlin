package com.linwei.cams.module.home.ui.search

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.common.utils.InputUtils
import com.linwei.cams.component.database.entity.SearchHistoryEntity
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.module.home.R
import com.linwei.cams.module.home.databinding.HomeActivitySearchBinding
import com.linwei.cams.module.home.ui.search.adapter.SearchClearAdapter
import com.linwei.cams.module.home.ui.search.adapter.SearchHisAdapter
import com.linwei.cams.module.home.ui.search.adapter.SearchHotAdapter
import com.linwei.cams.module.home.ui.search.mvp.contract.ISearchView
import com.linwei.cams.module.home.ui.search.mvp.presenter.SearchPresenter
import com.linwei.cams.module.home.ui.searchresult.SearchResultActivity
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.SearchBean
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = HomeRouterTable.PATH_ACTIVITY_SEARCH)
class SearchActivity : MvpBaseActivity<HomeActivitySearchBinding, SearchPresenter>(), ISearchView {
    private var mVirtualLayoutManager: VirtualLayoutManager? = null
    private var mDelegateAdapter: DelegateAdapter? = null

    private val mAdapters = mutableListOf<DelegateAdapter.Adapter<*>>()

    private var mSearchHisAdapter: SearchHisAdapter? = null
    private var mSearchClearAdapter: SearchClearAdapter? = null
    private var mSearchHotAdapter: SearchHotAdapter? = null

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
        mVirtualLayoutManager = VirtualLayoutManager(mContext)
        mDelegateAdapter = DelegateAdapter(mVirtualLayoutManager)

        mSearchHisAdapter =
            SearchHisAdapter(mutableListOf()).apply {
                mAdapters.add(this)
            }

        mSearchClearAdapter = SearchClearAdapter().apply {
            mAdapters.add(this)
        }

        mSearchHotAdapter = SearchHotAdapter(mutableListOf()).apply {
            mAdapters.add(this)
        }

        mDelegateAdapter?.setAdapters(mAdapters)
        mViewBinding?.homeRecyclerView?.apply {
            layoutManager = mVirtualLayoutManager
            adapter = mDelegateAdapter
        }

        mViewBinding?.homeSearchView?.let {
            InputUtils.showSoftInput(it)
        }
    }

    override fun initData() {
        mMvpPresenter?.requestHotSearchData()
    }

    override fun initEvent() {
        mViewBinding?.homeBackView?.setOnClickListener {
            finishAfterTransition()

            mViewBinding?.homeSearchView?.let {
                InputUtils.hideInputMethod(it, this@SearchActivity)
            }
        }

        mSearchClearAdapter?.setOnSearchClearCallBack(object :
            SearchClearAdapter.OnSearchClearCallBack {
            override fun onClear() {
                mMvpPresenter?.deleteAllLocalHis()
                mSearchHisAdapter?.clear()
                mViewBinding?.homeSearchView?.let {
                    InputUtils.hideInputMethod(it, this@SearchActivity)
                }
            }
        })

        mSearchHisAdapter?.setOnSearchHisCallBack(object : SearchHisAdapter.OnSearchHisCallBack {
            override fun onItemClick(searchContent: String?) {
                searchContent?.let {
                    skipResult(it)
                }
            }

            override fun onItemDelete(id: Long) {
                mMvpPresenter?.deleteLocalHisById(id)
            }
        })

        mSearchHotAdapter?.setOnSearchHotCallBack(object : SearchHotAdapter.OnSearchHotCallBack {
            override fun onHot(title: String?) {
                title?.let {
                    skipResult(it)
                }
            }
        })

        mViewBinding?.homeSearchView?.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val his: String = mViewBinding?.homeSearchView?.text.toString().trim()
                    if (his.isNotEmpty()) {
                        skipResult(his)
                        mMvpPresenter?.saveLocalHis(his)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mMvpPresenter?.selectLocalHis()
    }

    private fun skipResult(keyword: String) {
        mViewBinding?.homeSearchView?.let {
            it.setText(keyword)
            val p = Pair.create<View, String>(it, "search")
            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                p
            )
            SearchResultActivity.launch(this, keyword, optionsCompat)
        }
    }

    override fun hotSearchDataToView(list: List<SearchBean.SearchDetailsBean>) {
        if (list.isNotNullOrSize()) {
            val searchList: MutableList<SearchBean> = mutableListOf()
            searchList.add(SearchBean(getString(R.string.home_hot_search), list))
            mSearchHotAdapter?.refresh(searchList)
        }
    }

    override fun selectLocalHisDataToView(list: List<SearchHistoryEntity>?) {
        if (list?.isNotNullOrSize() == true) {
            val searchHistoryList = list.toMutableList().apply {
                reverse()
            }
            mSearchHisAdapter?.refresh(searchHistoryList)
            mSearchClearAdapter?.refreshStatus(true)
        } else {
            mSearchClearAdapter?.refreshStatus(false)
        }
    }

    override fun getPresenter(): SearchPresenter = SearchPresenter(this)

}