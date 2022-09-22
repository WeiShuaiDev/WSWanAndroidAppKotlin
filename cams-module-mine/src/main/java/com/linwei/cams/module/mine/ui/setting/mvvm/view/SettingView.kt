package com.linwei.cams.module.mine.ui.setting.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.setting.mvvm.viewmodel.SettingViewModel
import com.linwei.cams.service.mine.model.MyShareBean

interface SettingView : IMvvmView<SettingViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: SettingViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.chcheSize.observe(owner) { size->
                cacheDataToView(size)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun cacheDataToView(size: String)

}