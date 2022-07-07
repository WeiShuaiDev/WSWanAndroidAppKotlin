package com.linwei.cams.module.project.ui.project

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.weight.slidinguppanel.SlidingUpPanelLayout
import com.linwei.cams.framework.mvi.base.MviBaseFragment
import com.linwei.cams.module.project.databinding.ProjectFragmentProjectBinding
import com.linwei.cams.module.project.ui.project.mvi.intent.ProjectViewModel
import com.linwei.cams.module.project.ui.project.mvi.view.ProjectView
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.model.ProjectBean
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

    override fun immersionBar(): Boolean = true

    private var mProjectPage = Page<ProjectBean>()
    private val mProjectList = mutableListOf<ProjectBean>()

    private val mProjectTreeList = mutableListOf<ProjectTreeBean>()

    override fun initView() {

    }

    override fun initData() {
        mViewBinding.projectContentRefreshLayout.autoRefresh()
        //mViewModel?.fetchProjectTreeData()
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
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mCurPage++
                if (mCurPage <= mProjectPage.pageCount) {
                    mViewModel?.fetchProjectData(mCurPage, "")
                } else {
                    mViewBinding.projectContentRefreshLayout.finishLoadMoreWithNoMoreData()
                }
            }
        })
    }

    override fun projectTreeDataToView(data: List<ProjectTreeBean>) {

    }

    override fun projectDataToView(page: Page<ProjectBean>) {
        mProjectPage = page

        page.datas?.let {
            val positionStart: Int
            if (mCurPage == 0) {
                positionStart = 0
                mProjectList.clear()
            } else {
                positionStart = mProjectList.size
            }
            mProjectList.addAll(it)
            //mHomeArticleAdapter?.notifyItemRangeChanged(positionStart, it.size)
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