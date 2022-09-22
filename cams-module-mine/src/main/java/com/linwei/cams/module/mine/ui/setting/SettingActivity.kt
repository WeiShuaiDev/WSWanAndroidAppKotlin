package com.linwei.cams.module.mine.ui.setting

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.cache.mmkv.AppDataMMkvProvided
import com.linwei.cams.component.cache.utils.CacheUtils
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.common.utils.LanguageUtils
import com.linwei.cams.component.common.utils.getVersionName
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivitySettingBinding
import com.linwei.cams.module.mine.ui.setting.mvvm.view.SettingView
import com.linwei.cams.module.mine.ui.setting.mvvm.viewmodel.SettingViewModel
import com.linwei.cams.service.mine.MineRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_ACTIVITY_SETTING)
class SettingActivity : MvvmBaseActivity<MineActivitySettingBinding, SettingViewModel>(),
    SettingView {

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_activity_setting

    override fun initView() {
        mDataBinding?.apply {
            mViewModel?.title?.set(R.string.mine_set.idToString())
            viewModel = mViewModel
            activity = this@SettingActivity
        }
    }

    override fun initData() {
        val versionName = getVersionName()
        mDataBinding?.mineVersionView?.text=versionName

        val currentLanguage = LanguageUtils.getCurrentLanguage {
            AppDataMMkvProvided().getLanguage()
        }
        mDataBinding?.mineLanguageView?.text=String.format("%s-%s", currentLanguage.language, currentLanguage.country)

        mViewModel?.getCacheFileSize()
    }

    override fun initEvent() {

    }

    override fun cacheDataToView(size: String) {
        mDataBinding?.mineCacheView?.text = size
    }
}