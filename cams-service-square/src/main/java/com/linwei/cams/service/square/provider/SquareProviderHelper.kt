package com.linwei.cams.service.square.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.square.SquareRouterTable

object SquareProviderHelper {
    /**
     * SquareProvider
     */
    fun getSquareProvider(): SquareProvider? =
        ARouter.getInstance().build(SquareRouterTable.PATH_SERVICE_SQUARE)
            .navigation() as SquareProvider?

    /**
     * SquareActivity
     */
    fun jumpSquareActivity() =
        ARouter.getInstance().build(SquareRouterTable.PATH_ACTIVITY_SQUARE).navigation()

    /**
     * SquareFragment
     */
    fun jumpSquareFragment(title: String) =
        ARouter.getInstance().build(SquareRouterTable.PATH_FRAGMENT_SQUARE)
            .withString("title", title).navigation()
}