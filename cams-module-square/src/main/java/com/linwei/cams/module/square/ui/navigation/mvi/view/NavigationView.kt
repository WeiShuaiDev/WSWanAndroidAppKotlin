package com.linwei.cams.module.square.ui.square.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeOnlyState
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.IMviView
import com.linwei.cams.module.square.ui.square.mvi.intent.NavigationViewModel
import com.linwei.cams.module.square.ui.square.mvi.model.NavigationViewState
import com.linwei.cams.service.square.model.SquareTreeBean

interface NavigationView : IMviView<NavigationViewModel> {

    override fun bindViewModel(viewModel: NavigationViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                observeOnlyState(owner, NavigationViewState::squareNavisList) { data ->
                    squareNavisDataToView(data)
                }
                observeState(owner, NavigationViewState::fetchStatus) { fetchStatus ->
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
     * 广场导航数据更新到View
     * @param data [SquareTreeBean]
     */
    fun squareNavisDataToView(data: List<SquareTreeBean>)
}