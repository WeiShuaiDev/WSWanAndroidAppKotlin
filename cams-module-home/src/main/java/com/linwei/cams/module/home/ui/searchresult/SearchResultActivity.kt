package com.linwei.cams.module.home.ui.searchresult

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvp.base.MvpBaseActivity
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.module.common.adapter.CommonArticleListAdapter
import com.linwei.cams.module.home.R
import com.linwei.cams.module.home.databinding.HomeActivitySearchResultBinding
import com.linwei.cams.module.home.ui.search.SearchActivity
import com.linwei.cams.module.home.ui.searchresult.mvp.contract.ISearchResultView
import com.linwei.cams.module.home.ui.searchresult.mvp.presenter.SearchResultPresenter
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.HomeRouterTable
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = HomeRouterTable.PATH_ACTIVITY_SEARCH_RESULT)
class SearchResultActivity :
    MvpBaseActivity<HomeActivitySearchResultBinding, SearchResultPresenter>(), ISearchResultView {

    private var mCurPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    private var mCommonArticlePage = Page<CommonArticleBean>()

    private val mCommonArticleList = mutableListOf<CommonArticleBean>()

    private var mCommonArticleListAdapter: CommonArticleListAdapter? = null

    private val mKeyWord: String by lazy {
        intent.getStringExtra(KEYWORD) ?: ""
    }

    companion object {
        private const val KEYWORD = "keyword"

        fun launch(
            activity: SearchActivity,
            keyword: String,
            optionsCompat: ActivityOptionsCompat
        ) {
            val intent = Intent(activity, SearchResultActivity::class.java)
            intent.putExtra(KEYWORD, keyword)
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle())
        }
    }

    override fun initView() {
        mViewBinding?.homeSearchView?.text = mKeyWord

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, false)
        mViewBinding?.homeRecyclerView?.apply {
            addItemDecoration(
                CustomItemDecoration(
                    mContext,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )
            adapter = mCommonArticleListAdapter
        }
    }

    override fun initData() {
        mMvpPresenter?.requestSearchListData(mCurPage, mKeyWord)

    }

    override fun initEvent() {
        mViewBinding?.homeCancelView?.setOnClickListener {
            this.finish()
        }

        mViewBinding?.homeRefreshLayout?.setOnMultiListener(object : SimpleMultiListener() {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mCurPage = 0
                mMvpPresenter?.requestSearchListData(mCurPage, mKeyWord)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mCommonArticlePage.pageCount) {
                    mMvpPresenter?.requestSearchListData(mCurPage, mKeyWord)
                } else {
                    mViewBinding?.homeRefreshLayout?.finishLoadMoreWithNoMoreData()
                }
            }
        })

        mCommonArticleListAdapter?.setOnItemClickListener { _, _, _ ->
            //跳转到H5页面

        }

        mCommonArticleListAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.commonShineButtonView -> {
                    val articleBean = adapter.getItem(position) as CommonArticleBean
                    if (articleBean.collect) {
                        mMvpPresenter?.requestUnCollectStatus(articleBean.id)
                    } else {
                        mMvpPresenter?.requestCollectStatus(articleBean.id)
                    }
                    articleBean.collect = !articleBean.collect
                    mCommonArticleListAdapter?.notifyItemChanged(position)
                }
            }
        }
    }

    override fun getPresenter(): SearchResultPresenter = SearchResultPresenter(this)

    @SuppressLint("NotifyDataSetChanged")
    override fun commonArticleDataToView(page: Page<CommonArticleBean>) {
        //TODO BUG重复返回数据，所以无法notifyItemRangeChanged()
        mCommonArticlePage = page
        page.datas?.let {
            val positionStart: Int
            if (mCurPage == 0) {
                positionStart = 0
                mCommonArticleList.clear()
            } else {
                positionStart = mCommonArticleList.size
            }
            mCommonArticleList.addAll(it)
            mCommonArticleListAdapter?.notifyDataSetChanged()
        }
        mViewBinding?.homeRefreshLayout?.finishRefresh()
        mViewBinding?.homeRefreshLayout?.finishLoadMore()
    }

    override fun refreshCollectStatus(status: Boolean) {

    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mViewBinding?.homeRefreshLayout?.finishRefresh(false)
        } else {
            mViewBinding?.homeRefreshLayout?.finishLoadMore(false)
        }
    }
}