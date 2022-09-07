package com.linwei.cams.module.mine.ui.mycollect.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.mycollect.mvvm.viewmodel.MyCollectViewModel
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page

interface MyCollectView : IMvvmView<MyCollectViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: MyCollectViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.commonArticleBeanPage.observe(owner) { bean ->
                commonArticleDataToView(bean)
            }

            it.unCollectStatus.observe(owner) { status ->
                refreshUnCollectStatus(status)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun commonArticleDataToView(page: Page<CommonArticleBean>)

    fun refreshUnCollectStatus(status:Boolean)

}