package com.linwei.cams.module.square.ui.square.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.IMviView
import com.linwei.cams.module.square.ui.square.mvi.intent.SquareListViewModel
import com.linwei.cams.module.square.ui.square.mvi.model.SquareListViewState
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page

interface SquareListView : IMviView<SquareListViewModel> {

    override fun bindViewModel(viewModel: SquareListViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                observeState(owner, SquareListViewState::commonArticlePage) { data ->
                    commonArticleDataToView(data)
                }

                observeState(owner, SquareListViewState::collectStatus) { status ->
                    refreshCollectStatus(status)
                }

                observeState(owner, SquareListViewState::isRefresh) { isRefresh ->
                    refreshDataStatus(isRefresh)
                }

                observeState(owner, SquareListViewState::fetchStatus) { fetchStatus ->
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
     * 导航文章数据更新到View
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