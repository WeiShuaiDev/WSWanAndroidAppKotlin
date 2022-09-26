package com.linwei.cams.module.mine.ui.myscore

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityMyScoreBinding
import com.linwei.cams.module.mine.ui.myscore.mvvm.adapter.MyScoreHeaderAdapter
import com.linwei.cams.module.mine.ui.myscore.mvvm.adapter.MyScoreListAdapter
import com.linwei.cams.module.mine.ui.myscore.mvvm.view.MyScoreView
import com.linwei.cams.module.mine.ui.myscore.mvvm.viewmodel.MyScoreViewModel
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.model.RankBean
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_ACTIVITY_MY_SCORE)
class MyScoreActivity : MvvmBaseActivity<MineActivityMyScoreBinding, MyScoreViewModel>(),
    MyScoreView {

    private var mCurPage: Int = 0

    private val mVirtualLayoutManager: VirtualLayoutManager = VirtualLayoutManager(this)
    private val mDelegateAdapter: DelegateAdapter = DelegateAdapter(mVirtualLayoutManager)
    private val mAdapters = mutableListOf<DelegateAdapter.Adapter<*>>()

    private var mHeaderAdapter: MyScoreHeaderAdapter? = null

    private var mListAdapter: MyScoreListAdapter? = null

    private var mRankBeanPage = Page<RankBean>()
    private val mRankBeanList: MutableList<RankBean> = mutableListOf()

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_activity_my_score

    override fun initView() {
        mDataBinding?.apply {
            mViewModel?.title?.set(R.string.mine_integral.idToString(mContext))
            viewModel = mViewModel
            activity = this@MyScoreActivity
        }
        mHeaderAdapter = MyScoreHeaderAdapter().apply {
            mAdapters.add(this)
        }
        mListAdapter = MyScoreListAdapter(mRankBeanList).apply {
            mAdapters.add(this)
        }
        mDelegateAdapter.setAdapters(mAdapters)
        mDataBinding?.mineRecyclerView?.apply {
            layoutManager = mVirtualLayoutManager
            adapter = mDelegateAdapter
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
                mViewModel?.requestListIntegralData(mCurPage)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mRankBeanPage.pageCount) {
                    mViewModel?.requestListIntegralData(mCurPage)
                } else {
                    mDataBinding?.mineRefreshLayout?.finishLoadMoreWithNoMoreData()
                }
            }
        })
    }

    override fun rankDataToView(page: Page<RankBean>) {
        mRankBeanPage = page
        page.datas?.let {
            val positionStart: Int
            if (mCurPage == 0) {
                positionStart = 0
                mRankBeanList.clear()
            } else {
                positionStart = mRankBeanList.size
            }
            mRankBeanList.addAll(it)
            mListAdapter?.notifyItemRangeChanged(positionStart, it.size)
        }
        mDataBinding?.mineRefreshLayout?.finishRefresh()
        mDataBinding?.mineRefreshLayout?.finishLoadMore()
    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mDataBinding?.mineRefreshLayout?.finishRefresh(false)
        } else {
            mDataBinding?.mineRefreshLayout?.finishLoadMore(false)
        }
    }
}