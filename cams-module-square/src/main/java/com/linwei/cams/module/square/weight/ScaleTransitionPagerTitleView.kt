package com.linwei.cams.module.square.weight

import android.content.Context
import com.linwei.cams.module.square.ui.square.adapter.listener.OnTabSelectListener
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class ScaleTransitionPagerTitleView(
    context: Context,
    private val tabSelectListener: OnTabSelectListener?
) : ColorTransitionPagerTitleView(context) {

    override fun onSelected(index: Int, totalCount: Int) {
        super.onSelected(index, totalCount)
        tabSelectListener?.onSelect(index, totalCount)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        super.onDeselected(index, totalCount)
        tabSelectListener?.onDeselected(index, totalCount)
    }
}