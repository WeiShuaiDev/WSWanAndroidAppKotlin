package com.linwei.cams.service.login.provider

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.login.LoginRouterTable

object LoginProviderHelper {
    /**
     * LoginProvider
     */
    fun getLoginProvider(): LoginProvider? =
        ARouter.getInstance().build(LoginRouterTable.PATH_SERVICE_LOGIN)
            .navigation() as LoginProvider?

    /**
     * LoginActivity
     */
    fun jumpLoginActivity(): Activity? =
        ARouter.getInstance().build(LoginRouterTable.PATH_ACTIVITY_LOGIN).navigation() as Activity?
}