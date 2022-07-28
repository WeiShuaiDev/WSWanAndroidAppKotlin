package com.linwei.cams.module.square.ui.square.details.list

import android.annotation.SuppressLint
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
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
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener

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
        mViewBinding.topRootLayout.let {
            it.leftTitleView.setOnClickListener {
                this.finish()
            }
            it.titleView.text = title
        }

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, false)
        mViewBinding.squareRecyclerView.apply {
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
        mViewBinding.squareRefreshLayout.setOnMultiListener(object : SimpleMultiListener() {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mCurPage = 0
                mViewModel?.requestSquareTreeArticleListData(mCurPage, id)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mCommonArticlePage.pageCount) {
                    mViewModel?.requestSquareTreeArticleListData(mCurPage, id)
                } else {
                    mViewBinding.squareRefreshLayout.finishLoadMoreWithNoMoreData()
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
        mViewBinding.squareRefreshLayout.finishRefresh()
        mViewBinding.squareRefreshLayout.finishLoadMore()
    }

    override fun refreshCollectStatus(status: Boolean) {


    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mViewBinding.squareRefreshLayout.finishRefresh(false)
        } else {
            mViewBinding.squareRefreshLayout.finishLoadMore(false)
        }
    }
}