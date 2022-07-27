package com.linwei.cams.module.square.ui.square.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeOnlyState
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.MviView
import com.linwei.cams.module.square.ui.square.mvi.intent.SystemViewModel
import com.linwei.cams.module.square.ui.square.mvi.model.SystemViewState
import com.linwei.cams.service.square.model.SquareTreeBean

interface SystemView : MviView<SystemViewModel> {

    override fun bindViewModel(viewModel: SystemViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                observeOnlyState(owner, SystemViewState::squareTreeList) { data ->
                    squareTreeDataToView(data)
                }
                observeState(owner, SystemViewState::fetchStatus) { fetchStatus ->
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
     * 广场树数据更新到View
     * @param data [SquareTreeBean]
     */
    fun squareTreeDataToView(data: List<SquareTreeBean>)

}