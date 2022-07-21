package com.linwei.cams.module.main.ui.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.module.main.MainRouterTable
import com.linwei.cams.module.main.R
import com.linwei.cams.module.main.databinding.MainFragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MainRouterTable.PATH_FRAGMENT_MAIN)
class MainFragment : CommonBaseFragment<MainFragmentMainBinding>(){

    override fun getRootLayoutId(): Int = R.layout.main_fragment_main

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
    }

    override fun initData() {

    }

    override fun initEvent() {

    }
}