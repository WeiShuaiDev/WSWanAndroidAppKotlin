package com.linwei.cams.module.main.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.module.main.MainRouterTable
import com.linwei.cams.service.square.SquareRouterTable

object MainProviderHelper {

    /**
     * MainActivity
     */
    fun jumpMainActivity() =
        ARouter.getInstance().build(MainRouterTable.PATH_ACTIVITY_MAIN).navigation()
}