package com.linwei.cams.module.project.ui.project

import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.component.weight.slidinguppanel.SlidingUpPanelLayout
import com.linwei.cams.framework.mvi.base.MviBaseFragment
import com.linwei.cams.module.common.adapter.CommonArticleListAdapter
import com.linwei.cams.module.project.R
import com.linwei.cams.module.project.databinding.ProjectFragmentProjectBinding
import com.linwei.cams.module.project.ui.project.adapter.ProjectTitleAdapter
import com.linwei.cams.module.project.ui.project.mvi.intent.ProjectViewModel
import com.linwei.cams.module.project.ui.project.mvi.view.ProjectView
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = ProjectRouterTable.PATH_FRAGMENT_PROJECT)
class ProjectFragment : MviBaseFragment<ProjectFragmentProjectBinding, ProjectViewModel>(),
    ProjectView {

    @Autowired
    lateinit var title: String

    private var mCurPage: Int = 0

    override fun hasInjectARouter(): Boolean = true

    private var mCommonArticlePage = Page<CommonArticleBean>()
    private val mCommonArticleList = mutableListOf<CommonArticleBean>()

    private var mProjectTreeList = mutableListOf<ProjectTreeBean>()

    private var mProjectTitleAdapter: ProjectTitleAdapter? = null

    private var mCommonArticleListAdapter: CommonArticleListAdapter? = null

    private var mId: Int = -1

    override fun initView() {
        mProjectTitleAdapter = ProjectTitleAdapter(mProjectTreeList)
        mViewBinding.projectDragRecyclerView.apply {
            addItemDecoration(
                CustomItemDecoration(
                    activity,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )
            adapter = mProjectTitleAdapter
        }

        mCommonArticleListAdapter = CommonArticleListAdapter(mCommonArticleList, true)
        mViewBinding.projectContentRecyclerView.apply {
            addItemDecoration(
                CustomItemDecoration(
                    activity,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )
            adapter = mCommonArticleListAdapter
        }

        mViewBinding.projectSlidingLayout.setScrollableView(mViewBinding.projectDragRecyclerView)

    }

    override fun initData() {
        mViewModel?.requestProjectTreeData()
    }

    override fun initEvent() {
        mViewBinding.projectSlidingLayout.addPanelSlideListener(object :
            SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {

            }
        })

        mViewBinding.projectSlidingLayout.setFadeOnClickListener {
            mViewBinding.projectSlidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        mViewBinding.projectContentRefreshLayout.setOnMultiListener(object : SimpleMultiListener() {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mCurPage = 0
                mViewModel?.requestProjectData(mCurPage, mId.toString())

            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage <= mCommonArticlePage.pageCount) {
                    mViewModel?.requestProjectData(mCurPage, mId.toString())
                } else {
                    mViewBinding.projectContentRefreshLayout.finishLoadMoreWithNoMoreData()
                }
            }
        })

        mProjectTitleAdapter?.setOnItemClickListener { adapter, view, position ->
            mViewBinding.projectSlidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            (adapter.getItem(position) as ProjectTreeBean?)?.let {
                mViewBinding.projectDragTitleView.text = it.name
                this.mId = it.id
                this.mCurPage = 0
                mViewModel?.requestProjectData(mCurPage, mId.toString())
            }
        }

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

    override fun projectTreeDataToView(data: List<ProjectTreeBean>) {
        if (data.isNotNullOrSize()) {
            this.mProjectTreeList = data.toMutableList()

            mProjectTitleAdapter?.setList(data)

            data[0].let {
                mViewBinding.projectDragTitleView.text = it.name
                this.mId = it.id
                this.mCurPage = 0
                mViewModel?.requestProjectData(mCurPage, mId.toString())
            }
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
            mCommonArticleListAdapter?.notifyDataSetChanged()
        }
        mViewBinding.projectContentRefreshLayout.finishRefresh()
        mViewBinding.projectContentRefreshLayout.finishLoadMore()
    }

    override fun refreshCollectStatus(status: Boolean) {

    }

    override fun refreshDataStatus(isRefresh: Boolean) {
        if (isRefresh) {
            mViewBinding.projectContentRefreshLayout.finishRefresh(false)
        } else {
            mViewBinding.projectContentRefreshLayout.finishLoadMore(false)
        }
    }

}