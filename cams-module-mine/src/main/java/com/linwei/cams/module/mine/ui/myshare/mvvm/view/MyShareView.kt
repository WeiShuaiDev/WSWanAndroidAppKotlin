package com.linwei.cams.module.mine.ui.myshare.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.mine.ui.myshare.mvvm.viewmodel.MyShareViewModel
import com.linwei.cams.service.mine.model.MyShareBean

interface MyShareView : IMvvmView<MyShareViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: MyShareViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.myShareBean.observe(owner) { bean ->
                myShareDataToView(bean)
            }

            it.collectStatus.observe(owner) { status ->
                refreshCollectStatus(status)
            }

            it.deleteArticleStatus.observe(owner) { status ->
                refreshDeleteArticleStatus(status)
            }
        }
    }

    override fun refreshDataStatus(isRefresh: Boolean) {

    }

    fun myShareDataToView(bean: MyShareBean)

    fun refreshCollectStatus(status: Boolean)

    fun refreshDeleteArticleStatus(status: Boolean)

}