package com.linwei.cams.service.mine.provider

import android.app.Activity
import androidx.fragment.app.Fragment
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
    fun jumpMineActivity(title: String): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MINE)
            .withString("title", title).navigation() as Activity?

    /**
     * MineFragment
     */
    fun jumpMineFragment(title: String): Fragment? =
        ARouter.getInstance().build(MineRouterTable.PATH_FRAGMENT_MINE)
            .withString("title", title).navigation() as Fragment?

}