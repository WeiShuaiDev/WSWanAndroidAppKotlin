package com.linwei.cams.module.publis.ui.publis.mvvm.adapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.component.weight.hivelayoutmanager.HiveDrawable
import com.linwei.cams.component.weight.hivelayoutmanager.HiveLayoutManager
import com.linwei.cams.module.publis.R
import com.linwei.cams.service.publis.model.PublisAuthorBean

class PublisAuthorAdapter(
    data: MutableList<PublisAuthorBean>,
    private val listener: OnItemClickListener?
) : BaseQuickAdapter<PublisAuthorBean,
        BaseViewHolder>(R.layout.publis_item_author, data) {

    override fun convert(holder: BaseViewHolder, item: PublisAuthorBean) {
        holder.setText(R.id.publisTitleView, item.name)
        holder.getView<ImageView>(R.id.publisImageView).let {
            val hiveDrawable = HiveDrawable(
                HiveLayoutManager.VERTICAL,
                ContextCompat.getColor(context, R.color.colorPrimaryText)
            )
            it.setImageDrawable(hiveDrawable)
            it.setOnClickListener { listener?.onItemClick(holder.layoutPosition) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}