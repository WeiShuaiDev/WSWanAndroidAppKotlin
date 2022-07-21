package com.linwei.cams.module.common.listener

import androidx.fragment.app.Fragment

interface TabPagerListener {

    fun getFragment(position: Int): Fragment

    fun count(): Int
}