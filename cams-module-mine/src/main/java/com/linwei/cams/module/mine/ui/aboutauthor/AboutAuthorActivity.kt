package com.linwei.cams.module.mine.ui.aboutauthor

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.base.CommonBaseActivity
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityAboutAuthorBinding
import com.linwei.cams.service.mine.MineRouterTable

@Route(path = MineRouterTable.PATH_ACTIVITY_ABOUT_AUTHOR)
class AboutAuthorActivity : CommonBaseActivity<MineActivityAboutAuthorBinding>() {

    override fun hasInjectARouter(): Boolean = true

    override fun initView() {
        mViewBinding?.topRootLayout?.let {
            it.titleView.text = R.string.mine_about_author.idToString(mContext)
            it.leftTitleView.setOnClickListener {
                this.finish()
            }
        }
    }

    override fun initData() {

    }

    override fun initEvent() {

    }

}