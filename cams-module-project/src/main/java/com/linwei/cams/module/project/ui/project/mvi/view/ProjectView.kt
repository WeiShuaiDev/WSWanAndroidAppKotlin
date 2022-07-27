package com.linwei.cams.module.project.ui.project.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeOnlyState
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.MviView
import com.linwei.cams.module.project.ui.project.mvi.intent.ProjectViewModel
import com.linwei.cams.module.project.ui.project.mvi.model.MviViewState
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.project.model.ProjectTreeBean

interface ProjectView : MviView<ProjectViewModel> {

    override fun bindViewModel(viewModel: ProjectViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                observeOnlyState(owner, MviViewState::projectTreeList) { data ->
                    projectTreeDataToView(data)
                }

                observeState(owner, MviViewState::commonArticlePage) { page ->
                    commonArticleDataToView(page)
                }

                observeState(owner, MviViewState::collectStatus) { status ->
                    refreshCollectStatus(status)
                }

                observeState(owner, MviViewState::isRefresh) { isRefresh ->
                    refreshDataStatus(isRefresh)
                }

                observeState(owner, MviViewState::fetchStatus) { fetchStatus ->
                    when (fetchStatus) {
                        is FetchStatus.Fetched -> {

                        }
                        is FetchStatus.NotFetched -> {

                        }
                        is FetchStatus.Fetching -> {

                        }
                    }
                }
            }
        }
    }

    override fun bindOtherMviViewEvent(event: MviViewEvent) {
        super.bindOtherMviViewEvent(event)
        //扩展Event监听
    }

    /**
     * 项目树数据更新到View
     * @param data [ProjectTreeBean]
     */
    fun projectTreeDataToView(data: List<ProjectTreeBean>)

    /**
     * 项目文章数据更新到View
     * @param page [CommonArticleBean]
     */
    fun commonArticleDataToView(page: Page<CommonArticleBean>)

    /**
     * 更新收藏状态
     * @param status [Boolean]
     */
    fun refreshCollectStatus(status: Boolean)

    /**
     * 更新刷新状态
     * @param isRefresh [Boolean]
     */
    fun refreshDataStatus(isRefresh: Boolean)
}