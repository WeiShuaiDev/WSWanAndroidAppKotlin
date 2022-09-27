package com.linwei.cams.module.mine.ui.sharearticle

import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.ktx.idToString
import com.linwei.cams.component.mvvm.base.MvvmBaseActivity
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.databinding.MineActivityShareArticleBinding
import com.linwei.cams.module.mine.ui.sharearticle.mvvm.view.ShareArticleView
import com.linwei.cams.module.mine.ui.sharearticle.mvvm.viewmodel.ShareArticleViewModel
import com.linwei.cams.service.mine.MineRouterTable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = MineRouterTable.PATH_ACTIVITY_SHARE_ARTICLE)
class ShareArticleActivity : MvvmBaseActivity<MineActivityShareArticleBinding, ShareArticleViewModel>(), ShareArticleView {

    override fun hasInjectARouter(): Boolean = true

    override fun getRootLayoutId(): Int = R.layout.mine_activity_share_article

    override fun initView() {
        mDataBinding?.apply {
            mViewModel?.title?.set(R.string.mine_share_article.idToString(mContext))
            viewModel = mViewModel
            activity = this@ShareArticleActivity
        }
    }

    override fun initData() {

    }

    override fun initEvent() {
    }

    override fun shareArticleDataToView(data: Any) {

    }
}