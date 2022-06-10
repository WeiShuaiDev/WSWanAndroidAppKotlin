package com.linwei.camsmodular.ui.welcome.mvi.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.observeOnlyState
import com.linwei.cams.framework.mvi.ktx.observeState
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.framework.mvi.mvi.view.MviView
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.camsmodular.ui.welcome.mvi.intent.WelcomeViewModel
import com.linwei.camsmodular.ui.welcome.mvi.model.MviViewState

interface WelcomeView : MviView<WelcomeViewModel> {

    override fun bindViewModel(viewModel: WelcomeViewModel?, owner: LifecycleOwner) {
        super.bindViewModel(viewModel, owner)
        viewModel?.let {
            it.viewState.run {
                observeOnlyState(owner, MviViewState::projectTreeList) {
                    projectTreeDataToView(it)
                }

                observeState(owner, MviViewState::fetchStatus) {
                    System.out.println("==============${it}")
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

    /**
     * 项目树数据更新到View
     * @param data [ProjectTreeBean]
     */
    fun projectTreeDataToView(data: List<ProjectTreeBean>?)


    override fun bindOtherMviViewEvent(event: MviViewEvent) {
        super.bindOtherMviViewEvent(event)
        //扩展Event监听

    }

}