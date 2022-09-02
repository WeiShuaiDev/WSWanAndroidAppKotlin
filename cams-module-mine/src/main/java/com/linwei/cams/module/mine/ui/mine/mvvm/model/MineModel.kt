package com.linwei.cams.module.mine.ui.mine.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.UserInfoBean
import javax.inject.Inject

class MineModel @Inject constructor(private val mineProvider: MineProviderImpl) : MvvmModel() {

    fun collectStatus(id: Int, callback: ResponseCallback<Any>) {
        mineProvider.collectStatus(id, callback)
    }

    fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        mineProvider.unCollectStatus(id, callback)
    }

    fun fetchIntegralData(callback: ResponseCallback<UserInfoBean>) {
        mineProvider.fetchIntegralData(callback)
    }

}