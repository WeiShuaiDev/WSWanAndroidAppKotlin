package com.linwei.cams.module.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.addFragment
import com.linwei.cams.module.main.databinding.MainActivityMainBinding
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.provider.HomeProviderHelper
import com.linwei.cams.service.login.LoginRouterTable
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.provider.MineProviderHelper
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.provider.ProjectProviderHelper
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.publis.provider.PublisProviderHelper
import com.linwei.cams.service.square.SquareRouterTable
import com.linwei.cams.service.square.provider.SquareProviderHelper

@Route(path = MainRouterTable.PATH_ACTIVITY_MAIN)
class MainActivity : CommonBaseActivity<MainActivityMainBinding>() {

    override fun initView() {
        HomeProviderHelper.getHomeProvider()?.routerHomeFragment()?.let {
            supportFragmentManager.addFragment(it, R.id.main_container_fl)
        }
    }

    override fun initData() {
    }

    override fun initEvent() {
        mViewBinding.mainHomeTv.setOnClickListener {
            HomeProviderHelper.jumpHomeActivity("首页")
        }
        mViewBinding.mainProjectTv.setOnClickListener {
            ProjectProviderHelper.jumpProjectActivity("项目")
        }
        mViewBinding.mainSquareTv.setOnClickListener {
            SquareProviderHelper.jumpSquareActivity("广场")
        }
        mViewBinding.mainPublisTv.setOnClickListener {
            PublisProviderHelper.jumpPublisActivity("公众号")
        }
        mViewBinding.mainMineTv.setOnClickListener {
            MineProviderHelper.jumpMineActivity("我的")
        }
    }
}