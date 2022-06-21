package com.linwei.cams.module.publis.ui.publis

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvvm.base.MvvmBaseFragment
import com.linwei.cams.module.publis.R
import com.linwei.cams.module.publis.databinding.PublisFragmentPublisBinding
import com.linwei.cams.module.publis.ui.publis.mvvm.view.PublisView
import com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel.PublisViewModel
import com.linwei.cams.service.publis.PublisRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = PublisRouterTable.PATH_FRAGMENT_PUBLIS)
class PublisFragment : MvvmBaseFragment<PublisFragmentPublisBinding, PublisViewModel>(),
    PublisView {

    override fun getRootLayoutId(): Int = R.layout.publis_fragment_publis

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}