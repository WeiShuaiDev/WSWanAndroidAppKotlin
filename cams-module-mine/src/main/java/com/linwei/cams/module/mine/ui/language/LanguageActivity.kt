package com.linwei.cams.module.mine.ui.language

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.common.utils.LanguageUtils
import com.linwei.cams.component.weight.CustomItemDecoration
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityLanguageBinding
import com.linwei.cams.module.mine.model.LanguageBean
import com.linwei.cams.module.mine.ui.language.adapter.LanguageListAdapter
import com.linwei.cams.service.mine.MineRouterTable
import java.util.*

@Route(path = MineRouterTable.PATH_ACTIVITY_LANGUAGE)
class LanguageActivity : CommonBaseActivity<MineActivityLanguageBinding>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
        mViewBinding?.topRootLayout?.let {
            it.titleView.text = R.string.mine_language_set.idToString(mContext)
            it.leftTitleView.setOnClickListener {
                this.finish()
            }
        }

        val languageListAdapter = LanguageListAdapter()
        mViewBinding?.mineRecyclerView?.apply {
            addItemDecoration(
                CustomItemDecoration(
                    mContext,
                    CustomItemDecoration.ItemDecorationDirection.VERTICAL_LIST,
                    R.drawable.linear_split_line
                )
            )
            adapter = languageListAdapter
        }
        val languageList = fetchLanguageData()
        languageListAdapter.setList(languageList)
    }

    /**
     * 获取语言设置数据
     */
    private fun fetchLanguageData(): MutableList<LanguageBean> {
        val list: MutableList<LanguageBean> = mutableListOf<LanguageBean>().apply {
            add(LanguageBean(Locale.SIMPLIFIED_CHINESE, R.string.mine_simplified_chinese.idToString(mContext)))
            add(LanguageBean(Locale.US, R.string.mine_english.idToString(mContext)))
        }
        val currentLanguage: Locale = LanguageUtils.getCurrentLanguage{
            AppDataMMkvProvided().getLanguage()
        }
        for (languageBean in list) {
            if (currentLanguage == languageBean.locale) {
                languageBean.press=true
                break
            }
        }
        return list
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}