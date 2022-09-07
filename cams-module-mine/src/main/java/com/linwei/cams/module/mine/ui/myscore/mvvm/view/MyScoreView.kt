package com.linwei.cams.module.mine.ui.myscore.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.myscore.mvvm.viewmodel.MyScoreViewModel
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.model.RankBean

interface MyScoreView : IMvvmView<MyScoreViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: MyScoreViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.rankBeanPage.observe(owner) { bean ->
                rankBeanPageDataToView(bean)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun rankBeanPageDataToView(page: Page<RankBean>)

}