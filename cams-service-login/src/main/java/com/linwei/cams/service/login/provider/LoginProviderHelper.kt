package com.linwei.cams.service.login.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.login.LoginRouterTable

object LoginProviderHelper {
    /**
     * LoginProvider
     */
    fun getLoginProvider(): LoginProvider?=
        ARouter.getInstance().build(LoginRouterTable.PATH_SERVICE_LOGIN).navigation() as LoginProvider?
}