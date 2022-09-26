package com.linwei.cams.module.mine.ui.aboutus

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.common.utils.getVersionName
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityAboutusBinding
import com.linwei.cams.service.mine.MineRouterTable

@Route(path = MineRouterTable.PATH_ACTIVITY_ABOUTUS)
class AboutusActivity : CommonBaseActivity<MineActivityAboutusBinding>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
        mViewBinding?.topRootLayout?.let {
            it.titleView.text = R.string.mine_open_source_project.idToString(mContext)
            it.leftTitleView.setOnClickListener {
                this.finish()
            }
        }
    }

    override fun initData() {
        val versionName = getVersionName()
        mViewBinding?.mineVersionView?.text = versionName
    }

    override fun initEvent() {
        mViewBinding?.mineOfficialWebsiteLayout?.setOnClickListener {

        }

        mViewBinding?.mineWebsiteContentLayout?.setOnClickListener {

        }

        mViewBinding?.mineProjectRepositoryLayout?.setOnClickListener {

        }

    }
}