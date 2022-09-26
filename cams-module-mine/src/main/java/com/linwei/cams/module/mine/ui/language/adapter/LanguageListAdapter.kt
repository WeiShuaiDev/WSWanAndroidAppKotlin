package com.linwei.cams.module.mine.ui.language.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.utils.ActivityStackManager
import com.linwei.cams.component.common.utils.LanguageUtils
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.model.LanguageBean
import com.linwei.cams.service.mine.provider.MineProviderHelper

class LanguageListAdapter :
    BaseQuickAdapter<LanguageBean, BaseViewHolder>(R.layout.mine_item_language_list) {

    override fun convert(holder: BaseViewHolder, item: LanguageBean) {
        holder.apply {
            setText(R.id.mineTitleView, item.desc)

            getView<ImageView>(R.id.mineImageView).apply {
                visibility = if (item.press)
                    View.VISIBLE
                else
                    View.INVISIBLE
            }

            itemView.setOnClickListener {
                val languageBean: LanguageBean = getItem(layoutPosition)
                LanguageUtils.switchLanguage(languageBean.locale){
                    AppDataMMkvProvided().saveLanguage(it)
                }
                //删除所有Activity
                ActivityStackManager.finishAllActivity()
                //跳转到首页
                MineProviderHelper.jumpMainActivity()
            }
        }
    }
}