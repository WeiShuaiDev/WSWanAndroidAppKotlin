package com.linwei.cams.service.home.provider

import android.app.Activity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.home.HomeRouterTable

object HomeProviderHelper {
    /**
     * HomeProvider
     */
    fun getHomeProvider(): HomeProvider =
        ARouter.getInstance().build(HomeRouterTable.PATH_SERVICE_HOME)
            .navigation() as HomeProvider

    /**
     * HomeActivity
     */
    fun jumpHomeActivity(title: String): Activity =
        ARouter.getInstance().build(HomeRouterTable.PATH_ACTIVITY_HOME)
            .withString("title", title).navigation() as Activity

    /**
     * HomeFragment
     */
    fun jumpHomeFragment(title: String): Fragment =
        ARouter.getInstance().build(HomeRouterTable.PATH_FRAGMENT_HOME)
            .withString("title", title).navigation() as Fragment

}