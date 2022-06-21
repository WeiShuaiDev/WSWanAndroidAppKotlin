package com.linwei.cams.service.mine.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.mine.MineRouterTable

object MineProviderHelper {
    /**
     * MineProvider
     */
    fun getMineProvider(): MineProvider? =
        ARouter.getInstance().build(MineRouterTable.PATH_SERVICE_MINE).navigation() as MineProvider?

    /**
     * MineActivity
     */
    fun jumpMineActivity(title: String) =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MINE)
            .withString("title", title).navigation()

    /**
     * MineFragment
     */
    fun jumpMineFragment(title: String) =
        ARouter.getInstance().build(MineRouterTable.PATH_FRAGMENT_MINE)
            .withString("title", title).navigation()

}