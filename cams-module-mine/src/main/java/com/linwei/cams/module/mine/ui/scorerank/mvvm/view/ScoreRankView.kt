package com.linwei.cams.module.mine.ui.scorerank.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.scorerank.mvvm.viewmodel.ScoreRankViewModel
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.model.RankBean

interface ScoreRankView : IMvvmView<ScoreRankViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: ScoreRankViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.rankBeanPage.observe(owner) { page ->
                rankDataToView(page)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun rankDataToView(page: Page<RankBean>)
}