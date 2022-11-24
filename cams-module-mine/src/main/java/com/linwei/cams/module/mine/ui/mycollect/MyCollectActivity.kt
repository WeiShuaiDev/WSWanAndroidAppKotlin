package com.linwei.cams.module.mine.ui.mycollect

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.component.web.ui.CommonWebActivity
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.module.common.adapter.CommonArticleListAdapter
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityMyCollectBinding
import com.linwei.cams.module.mine.ui.mycollect.mvvm.view.MyCollectView
import com.linwei.cams.module.mine.ui.mycollect.mvvm.viewmodel.MyCollectViewModel
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.MineRouterTable
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_ACTIVITY_MY_COLLECT)
class MyCollectActivity : MvvmBaseActivity<MineActivityMyCollectBinding, MyCollectViewModel>(),
    MyCollectView {

    private var mCurPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_activity_my_collect

    private var mCommonArticlePage = Page<CommonArticleBean>()
    private val mCommonArticleList = mutableListOf<CommonArticleBean>()

    private var mCommonArticleListAdapter: CommonArticleListAdapter? = null

    override fun initView() {
        mDataBinding?.apply {
            mViewModel?.title?.set(R.string.mine_collect.idToString(mContext))
            viewModel = mViewModel
            activity = this@MyCollectActivity
        }

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, false)
        mDataBinding?.mineRecyclerView?.apply {
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
        mDataBinding?.mineRefreshLayout?.autoRefresh()
    }

    override fun initEvent() {
        mDataBinding?.mineRefreshLayout?.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mCurPage = 0
                mViewModel?.requestListMyCollectData(mCurPage)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mCommonArticlePage.pageCount) {
                    mViewModel?.requestListMyCollectData(mCurPage)
                } else {
                    mDataBinding?.mineRefreshLayout?.finishLoadMoreWithNoMoreData()
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
                    mViewModel?.requestUnCollectStatus(articleBean.id)
                    mCommonArticleList.removeAt(position)
                    mCommonArticleListAdapter?.notifyItemRemoved(position)
                }
            }
        }
    }

    override fun commonArticleDataToView(page: Page<CommonArticleBean>) {
        this.mCommonArticlePage = page
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
        mDataBinding?.mineRefreshLayout?.finishRefresh()
        mDataBinding?.mineRefreshLayout?.finishLoadMore()
    }

    override fun refreshUnCollectStatus(status: Boolean) {

    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mDataBinding?.mineRefreshLayout?.finishRefresh(false)
        } else {
            mDataBinding?.mineRefreshLayout?.finishLoadMore(false)
        }
    }
}