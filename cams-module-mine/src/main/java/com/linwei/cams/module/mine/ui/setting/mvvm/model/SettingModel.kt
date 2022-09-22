package com.linwei.cams.module.mine.ui.setting.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.login.provider.LoginProviderHelper
import com.linwei.cams.service.mine.provider.MineProviderHelper
import javax.inject.Inject

class SettingModel @Inject constructor(private val mineProvider: MineProviderImpl) : MvvmModel() {

    private val mLoginProvider = LoginProviderHelper.getLoginProvider()

    fun logout(callback: ResponseCallback<Any>) {
        mLoginProvider?.logout(callback)
    }
}