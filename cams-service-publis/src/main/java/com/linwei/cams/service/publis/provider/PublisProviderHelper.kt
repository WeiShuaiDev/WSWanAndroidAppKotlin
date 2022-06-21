package com.linwei.cams.service.publis.provider

import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.service.publis.PublisRouterTable

object PublisProviderHelper {
    /**
     * PublisProvider
     */
    fun getPublisProvider(): PublisProvider? =
        ARouter.getInstance().build(PublisRouterTable.PATH_SERVICE_PUBLIS)
            .navigation() as PublisProvider?

    /**
     * PublisActivity
     */
    fun jumpPublisActivity(title: String) =
        ARouter.getInstance().build(PublisRouterTable.PATH_ACTIVITY_PUBLIS)
            .withString("title", title).navigation()

    /**
     * PublisFragment
     */
    fun jumpPublisFragment(title: String) =
        ARouter.getInstance().build(PublisRouterTable.PATH_FRAGMENT_PUBLIS)
            .withString("title", title).navigation()
}