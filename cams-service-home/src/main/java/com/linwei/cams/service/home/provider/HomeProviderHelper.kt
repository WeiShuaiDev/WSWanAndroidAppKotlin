package com.linwei.cams.service.home.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.service.home.HomeRouterTable

object HomeProviderHelper {
    /**
     * HomeProvider
     */
    fun getHomeProvider(): HomeProvider? =
        ARouter.getInstance().build(HomeRouterTable.PATH_SERVICE_HOME).navigation() as HomeProvider?

    /**
     * HomeActivity
     */
    fun jumpHomeActivity(title: String) =
        ARouter.getInstance().build(HomeRouterTable.PATH_ACTIVITY_HOME)
            .withString("title", title).navigation()

    /**
     * HomeFragment
     */
    fun jumpHomeFragment(title: String) =
        ARouter.getInstance().build(HomeRouterTable.PATH_FRAGMENT_HOME)
            .withString("title", title).navigation()


}