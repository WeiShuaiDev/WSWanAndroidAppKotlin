package com.linwei.cams.module.mine.ui.myshare

import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.idToColor
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.module.common.adapter.CommonArticleListAdapter
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityMyShareBinding
import com.linwei.cams.module.mine.ui.myshare.mvvm.view.MyShareView
import com.linwei.cams.module.mine.ui.myshare.mvvm.viewmodel.MyShareViewModel
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.model.MyShareBean
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.yanzhenjie.recyclerview.SwipeMenu
import com.yanzhenjie.recyclerview.SwipeMenuCreator
import com.yanzhenjie.recyclerview.SwipeMenuItem
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_ACTIVITY_MY_SHARE)
class MyShareActivity : MvvmBaseActivity<MineActivityMyShareBinding, MyShareViewModel>(),
    MyShareView {

    private var mCurPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_activity_my_share

    private var mCommonArticleListAdapter: CommonArticleListAdapter? = null

    private var mCommonArticlePage = Page<CommonArticleBean>()
    private val mCommonArticleList = mutableListOf<CommonArticleBean>()

    private var mCoinInfo: UserInfoBean? = null

    override fun initView() {
        mDataBinding?.apply {
            mViewModel?.title?.set(R.string.mine_share.idToString())
            viewModel = mViewModel
            activity = this@MyShareActivity
        }

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, false)

        mDataBinding?.mineRecyclerView?.isSwipeItemMenuEnabled = true
        mDataBinding?.mineRecyclerView?.setSwipeMenuCreator(object : SwipeMenuCreator {

            override fun onCreateMenu(leftMenu: SwipeMenu?, rightMenu: SwipeMenu?, position: Int) {
                val width = resources.getDimensionPixelSize(R.dimen.dp_70)
                val height = ViewGroup.LayoutParams.MATCH_PARENT
                val deleteItem: SwipeMenuItem = SwipeMenuItem(this@MyShareActivity)
                    .setText(R.string.mine_delete.idToString())
                    .setTextColor(R.color.colorGlobalWhite.idToColor())
                    .setBackgroundColorResource(android.R.color.holo_red_dark)
                    .setWidth(width)
                    .setHeight(height)
                rightMenu?.addMenuItem(deleteItem) // 在Item右侧添加一个菜单。
                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        })

        mDataBinding?.mineRecyclerView?.setOnItemMenuClickListener { menuBridge, adapterPosition ->
            menuBridge.closeMenu()
            val direction: Int = menuBridge.direction // 左侧还是右侧菜单。
            val menuPosition: Int =
                menuBridge.position // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    mViewModel?.requestDeleteArticle(
                        mCommonArticleList[adapterPosition].id
                    )
                    mCommonArticleList.removeAt(adapterPosition)
                    mCommonArticleListAdapter?.removeAt(adapterPosition)
                }
            }
        }
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
                mViewModel?.requestListMyShareData(mCurPage)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mCommonArticlePage.pageCount) {
                    mViewModel?.requestListMyShareData(mCurPage)
                } else {
                    mDataBinding?.mineRefreshLayout?.finishLoadMoreWithNoMoreData()
                }
            }
        })

        mCommonArticleListAdapter?.setOnItemClickListener { adapter, view, position ->
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

        mDataBinding?.mineFloatingActionView?.setOnClickListener {

        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mDataBinding?.mineRefreshLayout?.finishRefresh(false)
        } else {
            mDataBinding?.mineRefreshLayout?.finishLoadMore(false)
        }
    }

    override fun myShareDataToView(bean: MyShareBean) {
        this.mCommonArticlePage = bean.shareArticles
        this.mCoinInfo = bean.coinInfo
        bean.shareArticles.datas?.let {
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

    override fun refreshCollectStatus(status: Boolean) {

    }

    override fun refreshDeleteArticleStatus(status: Boolean) {

    }
}