package com.linwei.cams.module.publis.ui.publis

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.mvvm.base.MvvmBaseFragment
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.component.weight.hivelayoutmanager.HiveLayoutManager
import com.linwei.cams.module.common.adapter.CommonArticleListAdapter
import com.linwei.cams.module.publis.R
import com.linwei.cams.module.publis.databinding.PublisFragmentPublisBinding
import com.linwei.cams.module.publis.guillotine.animation.GuillotineAnimation
import com.linwei.cams.module.publis.ui.publis.mvvm.adapter.PublisAuthorAdapter
import com.linwei.cams.module.publis.ui.publis.mvvm.view.PublisView
import com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel.PublisViewModel
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.publis.model.PublisAuthorBean
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = PublisRouterTable.PATH_FRAGMENT_PUBLIS)
class PublisFragment : MvvmBaseFragment<PublisFragmentPublisBinding, PublisViewModel>(),
    PublisView {

    private var mCurPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    override fun immersionBar(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.publis_fragment_publis

    private var mCommonArticlePage = Page<CommonArticleBean>()
    private val mCommonArticleList = mutableListOf<CommonArticleBean>()

    private var mPublisAuthorList = mutableListOf<PublisAuthorBean>()

    private var mCommonArticleListAdapter: CommonArticleListAdapter? = null

    private var mPublisAuthorAdapter: PublisAuthorAdapter? = null

    private var mGuillotineAnimation: GuillotineAnimation? = null

    private var mId: String = ""

    private var mPublisItemRecyclerView: RecyclerView? = null

    companion object {
        private const val RIPPLE_DURATION: Long = 250
    }

    override fun initView() {
        mDataBinding?.viewmodel = mViewModel

        val guillotineMenu: View =
            LayoutInflater.from(context).inflate(R.layout.publis_guillotine, null)

        mPublisAuthorAdapter = PublisAuthorAdapter(mPublisAuthorList,
            object : PublisAuthorAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    mGuillotineAnimation?.close()
                    val publisAuthorBean: PublisAuthorBean = mPublisAuthorList[position]
                    mDataBinding?.publisTitleView?.text = publisAuthorBean.name
                    mId = publisAuthorBean.id ?: ""

                    mCurPage = 0
                    mViewModel?.requestPublicArticleListData(mCurPage, mId)
                }
            })
        mPublisItemRecyclerView =
            guillotineMenu.findViewById<RecyclerView?>(R.id.publisItemRecyclerView)?.apply {
                val layoutManager = HiveLayoutManager(HiveLayoutManager.VERTICAL)
                layoutManager.gravity = HiveLayoutManager.ALIGN_LEFT
                setLayoutManager(layoutManager)
                adapter = mPublisAuthorAdapter
            }

        mDataBinding?.publisContainerLayout?.addView(guillotineMenu)
        mGuillotineAnimation = GuillotineAnimation.GuillotineBuilder(
            guillotineMenu,
            guillotineMenu.findViewById(R.id.publisCloseView),
            mDataBinding?.publisCloseView
        ).setStartDelay(RIPPLE_DURATION)
            .setActionBarViewForAnimation(mDataBinding?.publisToolbarView)
            .setClosedOnStart(true)
            .build()

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, false)
        mDataBinding?.publisContentRecyclerView?.apply {
            addItemDecoration(
                CustomItemDecoration(
                    activity,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )

            adapter = mCommonArticleListAdapter
        }

        mDataBinding?.publisContentRefreshLayout?.setEnableLoadMore(true)
        mDataBinding?.publisContentRefreshLayout?.setEnableRefresh(true)

    }

    override fun initData() {
        mViewModel?.requestPublicAuthorData()
    }

    override fun initEvent() {
        mDataBinding?.publisContentRefreshLayout?.setOnRefreshLoadMoreListener(object :
            OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mCurPage = 0
                mViewModel?.requestPublicArticleListData(mCurPage, mId)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage < mCommonArticlePage.pageCount) {
                    mViewModel?.requestPublicArticleListData(mCurPage, mId)
                } else {
                    mDataBinding?.publisContentRefreshLayout?.finishLoadMoreWithNoMoreData()
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

    override fun authorDataToView(list: List<PublisAuthorBean>) {
        if (list.isNotNullOrSize()) {
            mPublisAuthorList = list.toMutableList()
            mPublisAuthorAdapter?.setNewInstance(mPublisAuthorList)
            val bean: PublisAuthorBean = mPublisAuthorList[0]
            mDataBinding?.publisTitleView?.text = bean.name
            mId = bean.id ?: ""

            mCurPage = 0
            mViewModel?.requestPublicArticleListData(mCurPage, mId)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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
            runLayoutAnimation(mDataBinding?.publisContentRecyclerView)
        }
        mDataBinding?.publisContentRefreshLayout?.finishRefresh()
        mDataBinding?.publisContentRefreshLayout?.finishLoadMore()
    }

    override fun refreshCollectStatus(status: Boolean) {

    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mDataBinding?.publisContentRefreshLayout?.finishRefresh(false)
        } else {
            mDataBinding?.publisContentRefreshLayout?.finishLoadMore(false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun runLayoutAnimation(recyclerView: RecyclerView?) {
        recyclerView?.apply {
            val context = context
            val animation =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
            layoutAnimation = animation
            adapter!!.notifyDataSetChanged()
            scheduleLayoutAnimation()
        }
    }
}