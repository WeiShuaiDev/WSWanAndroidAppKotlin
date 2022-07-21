package com.linwei.cams.module.square.ui.square.adapter.listener

import android.view.View

interface OnTabSelectListener {

    /**
     * 选中
     */
    fun onSelect(index: Int, totalCount: Int)

    /**
     * 未选中
     */
    fun onDeselected(index: Int, totalCount: Int)
}

interface OnTabClickListener {
    /**
     * 点击
     */
    fun onTabClick(view: View?, index: Int)
}
