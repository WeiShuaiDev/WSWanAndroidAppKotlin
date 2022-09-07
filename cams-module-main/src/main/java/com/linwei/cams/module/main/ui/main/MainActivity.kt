package com.linwei.cams.module.main.ui.main

import android.content.Intent
import android.graphics.Typeface
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.component.weight.bubblenavigation.listener.BubbleNavigationChangeListener
import com.linwei.cams.module.main.MainRouterTable
import com.linwei.cams.module.main.R
import com.linwei.cams.module.main.databinding.MainActivityMainBinding
import com.linwei.cams.module.common.adapter.CommonPageAdapter
import com.linwei.cams.module.common.listener.TabPagerListener
import com.linwei.cams.module.main.ui.main.mvvm.view.MainView
import com.linwei.cams.module.main.ui.main.mvvm.viewmodel.MainViewModel
import com.linwei.cams.service.home.provider.HomeProviderHelper
import com.linwei.cams.service.mine.provider.MineProviderHelper
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
        mDataBinding?.mainBubbleNavigationView?.apply {
            setTypeface(Typeface.createFromAsset(assets, "rubik.ttf"))
            setBadgeValue(0, null)
            setBadgeValue(1, null)
            setBadgeValue(2, null)
            setBadgeValue(3, null)
            setBadgeValue(4, null)
            setNavigationChangeListener(this@MainActivity)
        }

        mDataBinding?.mainViewPager?.apply {
            offscreenPageLimit = 5
            setScrollable(false)
            adapter = CommonPageAdapter(supportFragmentManager, this@MainActivity)
        }
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun onNavigationChanged(view: View?, position: Int) {
        mDataBinding?.mainViewPager?.setCurrentItem(position, false)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> isNullFragment(HomeProviderHelper.jumpHomeFragment("首页"))
            1 -> isNullFragment(ProjectProviderHelper.jumpProjectFragment("项目"))
            2 -> isNullFragment(SquareProviderHelper.jumpSquareFragment("广场"))
            3 -> isNullFragment(PublisProviderHelper.jumpPublisFragment("公众号"))
            4 -> isNullFragment(MineProviderHelper.jumpMineFragment("我的"))
            else -> null
        } as Fragment
    }

    override fun count(): Int = 5

    private fun isNullFragment(fragment: Fragment?): Fragment {
        return fragment ?: MainFragment()
    }
}