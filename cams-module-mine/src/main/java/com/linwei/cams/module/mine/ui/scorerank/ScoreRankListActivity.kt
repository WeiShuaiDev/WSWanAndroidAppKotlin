package com.linwei.cams.module.mine.ui.scorerank

import android.text.TextUtils
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.cache.mmkv.MMkvHelper
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityScoreRankListBinding
import com.linwei.cams.module.mine.ui.scorerank.mvvm.adapter.ScoreRankListAdapter
import com.linwei.cams.module.mine.ui.scorerank.mvvm.view.ScoreRankView
import com.linwei.cams.module.mine.ui.scorerank.mvvm.viewmodel.ScoreRankViewModel
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.model.RankBean
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.tencent.mmkv.MMKVContentProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_ACTIVITY_SCORE_RANK_LIST)
class ScoreRankListActivity :
    MvvmBaseActivity<MineActivityScoreRankListBinding, ScoreRankViewModel>(),
    ScoreRankView {

    private var mCurPage: Int = 0

    private var mListAdapter: ScoreRankListAdapter? = null

    private var mRankBeanPage = Page<RankBean>()
    private val mRankBeanList: MutableList<RankBean> = mutableListOf()

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_activity_score_rank_list

    override fun initView() {
        mDataBinding?.apply {
            mViewModel?.title?.set(R.string.mine_scoreboard.idToString(mContext))
            viewModel = mViewModel
            activity = this@ScoreRankListActivity
        }
        mListAdapter = ScoreRankListAdapter()
        mDataBinding?.mineRecyclerView?.apply {
            addItemDecoration(
                CustomItemDecoration(
                    mContext,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )
            adapter = mListAdapter
        }
        mListAdapter?.setList(mRankBeanList)

        AppDataMMkvProvided().getUserInfo()?.apply {
            mDataBinding?.mineNameView?.text = username
            mDataBinding?.mineCoinsView?.text = coinCount

            val rank: String? = rank
            if (!TextUtils.isEmpty(rank)) {
                if (rank == "1" || rank == "2" || rank == "3") {
                    mDataBinding?.mineLogoView?.visibility = View.VISIBLE
                    mDataBinding?.mineLogoView?.setImageResource(if (rank == "1") R.mipmap.mine_gold_crown else if (rank == "2") R.mipmap.mine_silver_crown else R.mipmap.mine_cooper_crown)
                    mDataBinding?.mineTextView?.visibility = View.GONE
                } else {
                    mDataBinding?.mineLogoView?.visibility = View.GONE
                    mDataBinding?.mineTextView?.visibility = View.VISIBLE
                    mDataBinding?.mineTextView?.text = rank
                }
            } else {
                mDataBinding?.mineTextView?.visibility = View.GONE
                mDataBinding?.mineLogoView?.visibility = View.GONE
            }
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
                mViewModel?.requestListScoreRankData(mCurPage)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mRankBeanPage.pageCount) {
                    mViewModel?.requestListScoreRankData(mCurPage)
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