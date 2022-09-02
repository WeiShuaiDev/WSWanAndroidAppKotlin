package com.linwei.cams.module.publis.ui.publis.mvvm.view

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.component.mvvm.mvvm.view.IMvvmView
import com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel.PublisViewModel
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.model.PublisAuthorBean

interface PublisView : IMvvmView<PublisViewModel> {

    override fun bindOtherMvvmViewEvent(viewModel: PublisViewModel?, owner: LifecycleOwner) {
        viewModel?.let {
            it.publicAuthorBeanList.observe(owner) { list ->
                authorDataToView(list)
            }

            it.commonArticleBean.observe(owner) { page ->
                commonArticleDataToView(page)
            }

            it.refreshCollectStatus.observe(owner) { status ->
                refreshCollectStatus(status)
            }
        }
    }

    fun authorDataToView(list: List<PublisAuthorBean>)

    fun commonArticleDataToView(page: Page<CommonArticleBean>)

    fun refreshCollectStatus(status:Boolean)

}