package com.linwei.cams.module.mine.ui.mycollect.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import javax.inject.Inject

class MyCollectModel @Inject constructor(private val mineProvider: MineProviderImpl) : MvvmModel() {

    fun fetchListMyCollectData(page: Int, callback: ResponseCallback<Page<CommonArticleBean>>) {
        mineProvider.fetchListMyCollectData(page, callback)
    }

    fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        mineProvider.unCollectStatus(id, callback)
    }
}