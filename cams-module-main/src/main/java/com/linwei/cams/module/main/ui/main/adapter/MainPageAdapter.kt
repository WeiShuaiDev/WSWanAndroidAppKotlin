package com.linwei.cams.module.main.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.linwei.cams.module.main.ui.main.adapter.listener.TabPagerListener

class MainPageAdapter(fm: FragmentManager, val listener: TabPagerListener) :
    FragmentPagerAdapter(fm) {

    override fun getCount(): Int = listener.count()

    override fun getItem(position: Int): Fragment = listener.getFragment(position)
}