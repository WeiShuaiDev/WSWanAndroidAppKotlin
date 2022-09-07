package com.linwei.cams.module.mine.ui.myscore.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.model.RankBean
import javax.inject.Inject

class MyScoreModel @Inject constructor(private val mineProvider: MineProviderImpl) : MvvmModel() {

    fun fetchListIntegralData(page: Int, callback: ResponseCallback<Page<RankBean>>) {
        mineProvider.fetchListIntegralData(page, callback)
    }
}