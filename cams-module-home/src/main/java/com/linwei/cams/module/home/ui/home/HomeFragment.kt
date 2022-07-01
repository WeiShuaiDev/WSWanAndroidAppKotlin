package com.linwei.cams.module.home.ui.home

import android.view.View
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.mvp.base.MvpBaseFragment
import com.linwei.cams.module.home.R
import com.linwei.cams.module.home.databinding.HomeFragmentHomeBinding
import com.linwei.cams.module.home.ui.home.adapter.HomeArticleAdapter
import com.linwei.cams.module.home.ui.home.adapter.HomeBannerAdapter
import com.linwei.cams.module.home.ui.home.mvp.contract.IHomeView
import com.linwei.cams.module.home.ui.home.mvp.presenter.HomePresenter
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.ArticleEntity
import com.linwei.cams.service.home.model.BannerEntity
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = HomeRouterTable.PATH_FRAGMENT_HOME)
class HomeFragment : MvpBaseFragment<HomeFragmentHomeBinding, HomePresenter>(), IHomeView {

    @Autowired
    lateinit var title: String

    private var mPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    override fun immersionBar(): Boolean = true

    private var mArticlePage = Page<ArticleEntity>()
    private val mArticleList = mutableListOf<ArticleEntity>()

    private val mBannerList = mutableListOf<BannerEntity>()

    private var mVirtualLayoutManager: VirtualLayoutManager? = null
    private var mDelegateAdapter: DelegateAdapter? = null

    private val mAdapters = mutableListOf<DelegateAdapter.Adapter<*>>()

    private var mHomeArticleAdapter: HomeArticleAdapter? = null

    private var mHomeBannerAdapter: HomeBannerAdapter? = null

    override fun initView() {
        mViewBinding.topRootLayout.let {
            it.leftTitleView.visibility = View.INVISIBLE
            it.titleView.text = R.string.app_name.idToString()
            it.rightImageView.visibility = View.VISIBLE
            it.rightImageView.setImageResource(R.drawable.ic_search_black_24dp)
        }

        mVirtualLayoutManager = VirtualLayoutManager(mContext)
        mDelegateAdapter = DelegateAdapter(mVirtualLayoutManager)

        mHomeBannerAdapter =
            HomeBannerAdapter(this, mBannerList).apply {
                mAdapters.add(this)
            }

        mHomeArticleAdapter =
            HomeArticleAdapter(list = mArticleList, onArticleCollectListener = object :
                HomeArticleAdapter.OnArticleCollectListener {
                override fun onCollect(articleBean: ArticleEntity?) {
                    articleBean?.let {
                        if (articleBean.collect) {
                            mMvpPresenter?.requestUnCollectStatus(it.id)
                        } else {
                            mMvpPresenter?.requestCollectStatus(it.id)
                        }
                    }
                }
            }).apply {
                mAdapters.add(this)
            }

        mDelegateAdapter?.setAdapters(mAdapters)
        mViewBinding.homeRecyclerView.apply {
            layoutManager = mVirtualLayoutManager
            adapter = mDelegateAdapter
        }
    }

    override fun initData() {
        mMvpPresenter?.requestBannerData()
        mMvpPresenter?.requestArticleData(mPage)
    }

    override fun initEvent() {
        mViewBinding.homeRefreshLayout.setOnMultiListener(object : SimpleMultiListener() {

            override fun onHeaderMoving(
                header: RefreshHeader?,
                isDragging: Boolean,
                percent: Float,
                offset: Int,
                headerHeight: Int,
                maxDragHeight: Int
            ) {
                ((mViewBinding.topRootLayout) as LinearLayout).setAlpha(1 - Math.min(percent, 1f))
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {

            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
            }
        })
    }

    override fun getPresenter(): HomePresenter = HomePresenter(this)

    override fun updateArticleDataToView(articlePage: Page<ArticleEntity>) {
        mArticlePage = articlePage
        articlePage.datas?.let {
            val positionStart = mArticleList.size
            mArticleList.addAll(it)
            mHomeArticleAdapter?.notifyItemRangeChanged(positionStart, it.size)

        }
        mViewBinding.homeRefreshLayout.finishRefresh()
        mViewBinding.homeRefreshLayout.finishLoadMore()
    }

    override fun updateBannerDataToView(bannerList: List<BannerEntity>) {
        mBannerList.clear()
        mBannerList.addAll(bannerList)
        mHomeBannerAdapter?.notifyItemRangeChanged(0, mBannerList.size)
    }

    override fun refreshCollectStatus(status: Boolean) {

    }
}