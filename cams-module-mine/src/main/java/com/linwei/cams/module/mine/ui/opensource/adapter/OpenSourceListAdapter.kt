package com.linwei.cams.module.mine.ui.opensource.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.model.OpenSourceBean

class OpenSourceListAdapter :
    BaseQuickAdapter<OpenSourceBean, BaseViewHolder>(R.layout.mine_item_open_source_list) {

    override fun convert(holder: BaseViewHolder, item: OpenSourceBean) {
        holder.apply {
            setText(R.id.mineTitleView, item.author)
            setText(R.id.mineContentView, item.content)
            itemView.setOnClickListener {
                //跳转到H5
            }
        }
    }
}