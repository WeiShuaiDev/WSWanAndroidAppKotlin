package com.linwei.cams.module.common.adapter

import android.text.TextUtils
import androidx.appcompat.widget.AppCompatImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.component.image.utils.GlideUtil
import com.linwei.cams.component.weight.shinebutton.ShineButton
import com.linwei.cams.module.common.R
import com.linwei.cams.service.base.constants.ItemTypeConstants
import com.linwei.cams.service.base.model.CommonArticleBean

/**
 * 文章通用适配器
 */
class CommonArticleListAdapter(data: MutableList<CommonArticleBean>, private val hasTop: Boolean) :
    BaseMultiItemQuickAdapter<CommonArticleBean, BaseViewHolder>(data) {

    init {
        addItemType(ItemTypeConstants.ARTICLE_ITEM_TEXT, R.layout.common_item_article_text)
        addItemType(ItemTypeConstants.ARTICLE_ITEM_TEXT_PIC, R.layout.common_item_article_text_pic)
        addChildClickViewIds(R.id.commonShineButtonView)
    }

    override fun convert(holder: BaseViewHolder, item: CommonArticleBean) {
        val superChapterName: String? = item.superChapterName
        val chapterName: String? = item.chapterName
        when (holder.itemViewType) {
            ItemTypeConstants.ARTICLE_ITEM_TEXT ->
                holder.setText(
                    R.id.commonChapterView,
                    if (TextUtils.isEmpty(superChapterName)) chapterName else String.format(
                        "%s·%s", superChapterName, chapterName
                    )
                )
                    .setText(R.id.commonTimeView, item.niceDate)
                    .setGone(R.id.commonTagView, !item.fresh)
            ItemTypeConstants.ARTICLE_ITEM_TEXT_PIC -> {
                val commonImageView: AppCompatImageView =
                    holder.getView(R.id.commonImageView)
                GlideUtil.getInstance().loadRoundImage(
                    commonImageView,
                    item.envelopePic,
                    8,
                    R.drawable.common_layer_img_placeholder
                )
                holder.setText(R.id.commonContentView, item.desc)
            }
        }
        holder.setText(R.id.commonTitleView, item.title)
            .setText(
                R.id.commonChapterView,
                if (TextUtils.isEmpty(item.author)) item.shareUser else item.author
            )
            .setGone(R.id.commonTopView, !(hasTop && (holder.layoutPosition == 0)))
        holder.getView<ShineButton>(R.id.commonShineButtonView).isChecked = item.collect
    }

    /**
     * 取消收藏，做单个删除
     */
    fun cancelCollect(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}