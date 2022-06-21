package com.linwei.cams.module.main.ui.main

import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.addFragment
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.component.weight.bubblenavigation.listener.BubbleNavigationChangeListener
import com.linwei.cams.module.main.MainRouterTable
import com.linwei.cams.module.main.R
import com.linwei.cams.module.main.databinding.MainActivityMainBinding
import com.linwei.cams.module.main.ui.main.adapter.listener.TabPagerListener
import com.linwei.cams.module.main.ui.main.view.MainView
import com.linwei.cams.module.main.ui.main.viewmodel.MainViewModel
import com.linwei.cams.service.home.provider.HomeProviderHelper
import com.linwei.cams.service.mine.provider.MineProviderHelper
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.provider.ProjectProviderHelper
import com.linwei.cams.service.publis.provider.PublisProviderHelper
import com.linwei.cams.service.square.provider.SquareProviderHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MainRouterTable.PATH_ACTIVITY_MAIN)
class MainActivity : MvvmBaseActivity<MainActivityMainBinding, MainViewModel>(), MainView,
    BubbleNavigationChangeListener, TabPagerListener {

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.main_activity_main

    override fun initView() {
    }

    override fun initData() {

    }

    override fun initEvent() {
        val homeFragment = HomeProviderHelper.jumpHomeFragment("首页")
        val projectFragment = ProjectProviderHelper.jumpProjectFragment("项目")
        val squareFragment = SquareProviderHelper.jumpSquareFragment("广场")
        val publisFragment = PublisProviderHelper.jumpPublisFragment("公众号")
        val mineFragment = MineProviderHelper.jumpMineFragment("我的")
    }

    override fun onNavigationChanged(view: View?, position: Int) {

    }

    override fun getFragment(position: Int): Fragment {
        return Fragment()
    }

    override fun count(): Int = 5
}