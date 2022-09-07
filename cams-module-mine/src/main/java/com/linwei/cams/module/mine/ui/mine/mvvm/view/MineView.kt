package com.linwei.cams.module.mine.ui.mine.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel.MineViewModel
import com.linwei.cams.service.base.model.UserInfoBean

interface MineView : IMvvmView<MineViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: MineViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.userInfoBean.observe(owner) { bean ->
                userInfoDataToView(bean)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun userInfoDataToView(bean: UserInfoBean)
}