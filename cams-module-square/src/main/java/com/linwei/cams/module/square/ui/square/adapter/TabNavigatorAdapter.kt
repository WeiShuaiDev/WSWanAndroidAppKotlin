package com.linwei.cams.module.square.ui.square.adapter

import android.content.Context
import android.util.TypedValue
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import com.linwei.cams.module.square.R
import com.linwei.cams.module.square.ui.square.adapter.listener.OnTabClickListener
import com.linwei.cams.module.square.ui.square.adapter.listener.OnTabSelectListener
import com.linwei.cams.module.square.weight.ScaleTransitionPagerTitleView
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

class TabNavigatorAdapter(
    private val tabNameList: List<String>,
    private val tabClickListener: OnTabClickListener?
) : CommonNavigatorAdapter() {

    override fun getCount(): Int = tabNameList.size

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        val scaleTransitionPagerTitleView =
            ScaleTransitionPagerTitleView(context).apply {
                text = tabNameList[index]
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                setPadding(40, 0, 40, 0)
                normalColor = ContextCompat.getColor(context, R.color.colorSecondaryText)
                selectedColor = ContextCompat.getColor(context, R.color.colorPrimaryText)
                setOnClickListener { view ->
                    tabClickListener?.onTabClick(view, index)
                }
                setOnTabSelectListener(object : OnTabSelectListener {
                    override fun onSelect(index: Int, totalCount: Int) {
                        paint.isFakeBoldText = true
                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        paint.isFakeBoldText = false
                    }
                })
            }
        return scaleTransitionPagerTitleView
    }

    override fun getIndicator(context: Context): IPagerIndicator {
        return LinePagerIndicator(context).apply {
            mode = LinePagerIndicator.MODE_EXACTLY
            lineHeight = UIUtil.dip2px(context, 3.0).toFloat()
            lineWidth = UIUtil.dip2px(context, 10.0).toFloat()
            roundRadius = UIUtil.dip2px(context, 3.0).toFloat()
            startInterpolator = AccelerateInterpolator()
            endInterpolator = DecelerateInterpolator(2.0f)
            setColors(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }
}