package com.linwei.cams.module.publis.ui.publis

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.module.publis.databinding.PublisActivityPublisBinding
import com.linwei.cams.service.publis.PublisRouterTable

@Route(path = PublisRouterTable.PATH_ACTIVITY_PUBLIS)
class PublisActivity : CommonBaseActivity<PublisActivityPublisBinding>(){

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}