package com.linwei.cams.module.mine.ui.myshare.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.model.MyShareBean
import com.linwei.cams.service.mine.model.RankBean
import javax.inject.Inject

class MyShareModel @Inject constructor(private val mineProvider: MineProviderImpl) : MvvmModel() {

    fun fetchListMyShareData(page: Int, callback: ResponseCallback<MyShareBean>) {
        mineProvider.fetchListMyShareData(page, callback)
    }

    fun deleteArticle(id: Int, callback: ResponseCallback<Any>) {
        mineProvider.deleteArticle(id, callback)
    }

    fun collectStatus(id: Int, callback: ResponseCallback<Any>) {
        mineProvider.collectStatus(id, callback)
    }

    fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        mineProvider.unCollectStatus(id, callback)
    }
}