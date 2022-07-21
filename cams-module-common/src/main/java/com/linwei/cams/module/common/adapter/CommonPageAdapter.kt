package com.linwei.cams.module.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.linwei.cams.module.common.listener.TabPagerListener

/**
 * 通用ViewPager适配器
 */
class CommonPageAdapter(fm: FragmentManager, val listener: TabPagerListener) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int = listener.count()

    override fun getItem(position: Int): Fragment = listener.getFragment(position)
}