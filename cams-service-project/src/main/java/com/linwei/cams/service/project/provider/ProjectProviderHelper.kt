package com.linwei.cams.service.project.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.project.ProjectRouterTable

object ProjectProviderHelper {
    /**
     * ProjectProvider
     */
    fun getProjectProvider(): ProjectProvider?=
        ARouter.getInstance().build(ProjectRouterTable.PATH_SERVICE_PROJECT).navigation() as ProjectProvider?

    /**
     * ProjectActivity
     */
    fun jumpProjectActivity(title: String) =
        ARouter.getInstance().build(ProjectRouterTable.PATH_ACTIVITY_PROJECT)
            .withString("title", title).navigation()
}