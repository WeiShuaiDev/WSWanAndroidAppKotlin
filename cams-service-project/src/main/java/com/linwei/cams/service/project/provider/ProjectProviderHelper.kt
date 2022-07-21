package com.linwei.cams.service.project.provider

import android.app.Activity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.project.ProjectRouterTable

object ProjectProviderHelper {
    /**
     * ProjectProvider
     */
    fun getProjectProvider(): ProjectProvider?=
        ARouter.getInstance().build(ProjectRouterTable.PATH_SERVICE_PROJECT)
            .navigation() as ProjectProvider?

    /**
     * ProjectActivity
     */
    fun jumpProjectActivity(title: String) :Activity?=
        ARouter.getInstance().build(ProjectRouterTable.PATH_ACTIVITY_PROJECT)
            .withString("title", title).navigation() as Activity?

    /**
     * ProjectFragment
     */
    fun jumpProjectFragment(title: String): Fragment? =
        ARouter.getInstance().build(ProjectRouterTable.PATH_FRAGMENT_PROJECT)
            .withString("title", title).navigation() as Fragment?
}