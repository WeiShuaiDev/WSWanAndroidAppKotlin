package com.linwei.cams.module.mine.ui.mine.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel.MineViewModel

interface MineView: IMvvmView<MineViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: MineViewModel?, owner: LifecycleOwner) {
    }

    override fun refreshDataStatus(isRefresh: Boolean) {
    }
}