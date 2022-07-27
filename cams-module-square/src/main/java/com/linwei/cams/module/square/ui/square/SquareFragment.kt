package com.linwei.cams.module.square.ui.square

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseFragment
import com.linwei.cams.module.common.adapter.CommonPageAdapter
import com.linwei.cams.module.common.listener.TabPagerListener
import com.linwei.cams.module.square.R
import com.linwei.cams.module.square.databinding.SquareFragmentSquareBinding
import com.linwei.cams.module.square.ui.square.adapter.TabNavigatorAdapter
import com.linwei.cams.module.square.ui.square.adapter.listener.OnTabClickListener
import com.linwei.cams.service.square.SquareRouterTable
import com.linwei.cams.service.square.provider.SquareProviderHelper
import dagger.hilt.android.AndroidEntryPoint
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

@AndroidEntryPoint
@Route(path = SquareRouterTable.PATH_FRAGMENT_SQUARE)
class SquareFragment : CommonBaseFragment<SquareFragmentSquareBinding>(),
    TabPagerListener {

    override fun hasInjectARouter(): Boolean = true

    override fun immersionBar(): Boolean = true

    override fun initView() {

        val commonNavigator = CommonNavigator(context)
        val stringArray = resources.getStringArray(R.array.square_tab_title)
        val tabNavigatorAdapter =
            TabNavigatorAdapter(listOf(*stringArray), object : OnTabClickListener {
                override fun onTabClick(view: View?, index: Int) {
                    mViewBinding.squareViewPager.currentItem = index
                }
            })
        commonNavigator.adapter = tabNavigatorAdapter
        mViewBinding.squareMagicIndicatorView.navigator = commonNavigator

        val commonPageAdapter = CommonPageAdapter(childFragmentManager, this)
        mViewBinding.squareViewPager.apply {
            offscreenPageLimit = stringArray.size
            adapter = commonPageAdapter
        }
        ViewPagerHelper.bind(mViewBinding.squareMagicIndicatorView, mViewBinding.squareViewPager)

    }

    override fun initData() {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initEvent() {
        mViewBinding.squareViewPager.setOnTouchListener { v, _ -> false }
    }

    override fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> SquareProviderHelper.jumpSystemFragment()
            1 -> SquareProviderHelper.jumpNavigationFragment()
            else -> null
        } as Fragment
    }

    override fun count(): Int = 2
}