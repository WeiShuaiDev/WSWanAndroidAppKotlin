package com.linwei.cams.module.mine.ui.sharearticle.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.sharearticle.mvvm.viewmodel.ShareArticleViewModel

interface ShareArticleView : IMvvmView<ShareArticleViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: ShareArticleViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.shareArticle.observe(owner) { data ->
                shareArticleDataToView(data)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun shareArticleDataToView(data: Any)


}