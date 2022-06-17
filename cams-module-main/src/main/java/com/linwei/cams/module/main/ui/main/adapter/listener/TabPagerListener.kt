package com.linwei.cams.module.main.ui.main.adapter.listener

import androidx.fragment.app.Fragment

interface TabPagerListener {

    fun getFragment(position: Int): Fragment

    fun count(): Int
}