package com.linwei.cams.service.publis.provider

import android.app.Activity
import androidx.fragment.app.Fragment
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
    fun jumpPublisActivity(title: String): Activity? =
        ARouter.getInstance().build(PublisRouterTable.PATH_ACTIVITY_PUBLIS)
            .withString("title", title).navigation() as Activity?

    /**
     * PublisFragment
     */
    fun jumpPublisFragment(title: String): Fragment? =
        ARouter.getInstance().build(PublisRouterTable.PATH_FRAGMENT_PUBLIS)
            .withString("title", title).navigation() as Fragment?
}