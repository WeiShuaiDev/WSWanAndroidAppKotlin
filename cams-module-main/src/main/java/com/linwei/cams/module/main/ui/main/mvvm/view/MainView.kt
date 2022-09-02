package com.linwei.cams.module.main.ui.main.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.main.ui.main.mvvm.viewmodel.MainViewModel

interface MainView : IMvvmView<MainViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: MainViewModel?, owner: LifecycleOwner) {
    }

    override fun refreshDataStatus(isRefresh: Boolean) {
    }
}