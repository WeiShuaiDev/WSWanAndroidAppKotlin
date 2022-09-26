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

    /**
     * MyScoreActivity
     */
    fun jumpMyScoreActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MY_SCORE)
            .navigation() as Activity?

    /**
     * MyCollectActivity
     */
    fun jumpMyCollectActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MY_COLLECT)
            .navigation() as Activity?

    /**
     * MyShareActivity
     */
    fun jumpMyShareActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MY_SHARE)
            .navigation() as Activity?

    /**
     * OpenSourceActivity
     */
    fun jumpOpenSourceActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_OPEN_SOURCE)
            .navigation() as Activity?

    /**
     * AboutAuthorActivity
     */
    fun jumpAboutAuthorActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_ABOUT_AUTHOR)
            .navigation() as Activity?

    /**
     * SettingActivity
     */
    fun jumpSettingActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_SETTING)
            .navigation() as Activity?

    /**
     * LanguageActivity
     */
    fun jumpLanguageActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_LANGUAGE)
            .navigation() as Activity?

    /**
     * AboutusActivity
     */
    fun jumpAboutusActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_ABOUTUS)
            .navigation() as Activity?

    /**
     * ScoreRankListActivity
     */
    fun jumpScoreRankListActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_SCORE_RANK_LIST)
            .navigation() as Activity?

    /**
     * MainActivity
     */
    fun jumpMainActivity(): Activity? =
        ARouter.getInstance().build(MineRouterTable.PATH_ACTIVITY_MAIN)
            .navigation() as Activity?
}