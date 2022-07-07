package com.linwei.cams.module.square.ui.square.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.MviView
import com.linwei.cams.module.square.ui.square.mvi.intent.SquareViewModel
import com.linwei.cams.module.square.ui.square.mvi.model.MviViewState

interface SquareView : MviView<SquareViewModel> {

    override fun bindViewModel(viewModel: SquareViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                //observeOnlyState


                observeState(owner, MviViewState::fetchStatus) {
                    when (it) {
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
}