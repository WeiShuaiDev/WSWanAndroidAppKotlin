package com.linwei.cams.module.square.ui.square.details.list

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.web.ui.CommonWebActivity
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.framework.mvi.base.MviBaseActivity
import com.linwei.cams.module.common.adapter.CommonArticleListAdapter
import com.linwei.cams.module.square.R
import com.linwei.cams.module.square.databinding.SquareActivitySquareListBinding
import com.linwei.cams.module.square.ui.square.mvi.intent.SquareListViewModel
import com.linwei.cams.module.square.ui.square.mvi.view.SquareListView
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.square.SquareRouterTable
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = SquareRouterTable.PATH_ACTIVITY_SQUARE_LIST)
class SquareListActivity : MviBaseActivity<SquareActivitySquareListBinding, SquareListViewModel>(),
    SquareListView {

    @Autowired
    lateinit var title: String

    @Autowired
    lateinit var id: String

    private var mCurPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    private var mCommonArticlePage = Page<CommonArticleBean>()
    private val mCommonArticleList = mutableListOf<CommonArticleBean>()

    private var mCommonArticleListAdapter: CommonArticleListAdapter? = null

    override fun initView() {
        mViewBinding?.topRootLayout?.let {
            it.leftTitleView.setOnClickListener {
                this.finish()
            }
            it.titleView.text = title
        }

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, false)
        mViewBinding?.squareRecyclerView?.apply {
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
        mViewModel?.requestSquareTreeArticleListData(mCurPage, id)
    }

    override fun initEvent() {
        mViewBinding?.squareRefreshLayout?.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mCurPage = 0
                mViewModel?.requestSquareTreeArticleListData(mCurPage, id)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mCommonArticlePage.pageCount) {
                    mViewModel?.requestSquareTreeArticleListData(mCurPage, id)
                } else {
                    mViewBinding?.squareRefreshLayout?.finishLoadMoreWithNoMoreData()
                }
            }
        })

        mCommonArticleListAdapter?.setOnItemClickListener { adapter, _, position ->
            val articleBean = adapter.getItem(position) as CommonArticleBean
            articleBean.link?.let {
                CommonWebActivity.start(it)
            }
        }

        mCommonArticleListAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val articleBean = adapter.getItem(position) as CommonArticleBean
            when (view.id) {
                R.id.commonShineButtonView -> {
                    if (articleBean.collect) {
                        mViewModel?.requestUnCollectStatus(articleBean.id)
                    } else {
                        mViewModel?.requestCollectStatus(articleBean.id)
                    }
                    articleBean.collect = !articleBean.collect
                    mCommonArticleListAdapter?.notifyItemChanged(position)
                }
            }
        }
    }

    override fun commonArticleDataToView(page: Page<CommonArticleBean>) {
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
            mCommonArticleListAdapter?.notifyItemRangeChanged(positionStart, it.size)
        }
        mViewBinding?.squareRefreshLayout?.finishRefresh()
        mViewBinding?.squareRefreshLayout?.finishLoadMore()
    }

    override fun refreshCollectStatus(status: Boolean) {

    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mViewBinding?.squareRefreshLayout?.finishRefresh(false)
        } else {
            mViewBinding?.squareRefreshLayout?.finishLoadMore(false)
        }
    }
}