package com.linwei.cams.service.square.provider

import android.app.Activity
import androidx.fragment.app.Fragment
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
    fun jumpSquareActivity(): Activity? =
        ARouter.getInstance().build(SquareRouterTable.PATH_ACTIVITY_SQUARE)
            .navigation() as Activity?

    /**
     * SquareListActivity
     */
    fun jumpSquareListActivity(title: String?, id: String?): Activity? =
        ARouter.getInstance().build(SquareRouterTable.PATH_ACTIVITY_SQUARE_LIST)
            .withString("title", title)
            .withString("id", id)
            .navigation() as Activity?

    /**
     * SquareFragment
     */
    fun jumpSquareFragment(title: String): Fragment? =
        ARouter.getInstance().build(SquareRouterTable.PATH_FRAGMENT_SQUARE)
            .withString("title", title).navigation() as Fragment?

    /**
     * SystemFragment
     */
    fun jumpSystemFragment(): Fragment? =
        ARouter.getInstance().build(SquareRouterTable.PATH_FRAGMENT_SYSTEM)
            .navigation() as Fragment?

    /**
     * NavigationFragment
     */
    fun jumpNavigationFragment(): Fragment? =
        ARouter.getInstance().build(SquareRouterTable.PATH_FRAGMENT_NAVIGATION)
            .navigation() as Fragment?
}