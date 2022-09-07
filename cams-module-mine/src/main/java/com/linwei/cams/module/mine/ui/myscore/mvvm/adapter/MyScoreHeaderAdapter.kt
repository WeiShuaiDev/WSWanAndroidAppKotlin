package com.linwei.cams.module.mine.ui.myscore.mvvm.adapter

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.linwei.cams.component.cache.utils.mmkv.AppDataMMkvProvided
import com.linwei.cams.module.mine.R

/**
 * 积分列表头部适配器
 */
class MyScoreHeaderAdapter() : DelegateAdapter.Adapter<MyScoreHeaderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyScoreHeaderAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.mine_item_my_score_header, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyScoreHeaderAdapter.ViewHolder, position: Int) {
        holder.apply {
            startAnim(mineScoreView)
        }
    }

    private fun startAnim(textView: TextView) {
        val coinCount: String? = AppDataMMkvProvided().getUserInfo()?.coinCount
        val valueAnimator = ValueAnimator.ofInt(0, coinCount?.toInt() ?: 0)
        //播放时长
        valueAnimator.duration = 1500
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { valueAnimator1: ValueAnimator ->
            //获取改变后的值
            val currentValue = valueAnimator1.animatedValue as Int
            textView.text = currentValue.toString()
        }
        valueAnimator.start()
    }

    override fun getItemCount(): Int = 1

    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mineScoreView =
            view.findViewById<TextView>(R.id.mineScoreView)
    }
}