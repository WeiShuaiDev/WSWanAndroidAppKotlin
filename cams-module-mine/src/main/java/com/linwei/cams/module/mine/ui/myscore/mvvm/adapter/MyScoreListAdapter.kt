package com.linwei.cams.module.mine.ui.myscore.mvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.linwei.cams.component.common.utils.DateUtils
import com.linwei.cams.module.mine.R
import com.linwei.cams.service.mine.model.RankBean

/**
 * 积分列表适配器
 */
class MyScoreListAdapter(private var list: List<RankBean>) :
    DelegateAdapter.Adapter<MyScoreListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyScoreListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.mine_item_my_score_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyScoreListAdapter.ViewHolder, position: Int) {
        val data: RankBean = list[position]
        holder.apply {
            data.let {
                mineTitleView.text = it.reason
                mineTimeView.text =
                    DateUtils.getDateFormatString(it.date, DateUtils.NORMAL_DATE_FORMAT)
                mineScoreView.text = String.format("+%s", it.coinCount)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mineTitleView: TextView =
            view.findViewById(R.id.mineTitleView)
        val mineTimeView: TextView =
            view.findViewById(R.id.mineTimeView)
        val mineScoreView: TextView =
            view.findViewById(R.id.mineScoreView)
    }
}