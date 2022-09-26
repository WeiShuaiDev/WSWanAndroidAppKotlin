package com.linwei.cams.module.mine.ui.scorerank.mvvm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.module.mine.R
import com.linwei.cams.service.mine.model.RankBean

class ScoreRankListAdapter :
    BaseQuickAdapter<RankBean, BaseViewHolder>(R.layout.mine_item_score_rank_list) {

    override fun convert(holder: BaseViewHolder, item: RankBean) {
        val rank: String? = item.rank
        holder.apply {
            setGone(R.id.mineLogoView, rank != "1" && rank != "2" && rank != "3")
            setImageResource(
                R.id.mineLogoView,
                if (rank == "1") R.mipmap.mine_gold_crown else if (rank == "2") R.mipmap.mine_silver_crown else R.mipmap.mine_cooper_crown
            )
            setGone(R.id.mineTextView, rank == "1" || rank == "2" || rank == "3")
            setText(R.id.mineTextView, rank)
            setText(R.id.mineNameView, item.userName)
            setText(R.id.mineCoinsView, item.coinCount)
        }
    }
}