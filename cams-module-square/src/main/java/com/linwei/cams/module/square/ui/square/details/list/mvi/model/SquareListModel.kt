package com.linwei.cams.module.square.ui.square.mvi.model

import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page

data class SquareListViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,

    val commonArticlePage: Page<CommonArticleBean> = Page(),

    val collectStatus: Boolean = false,

    val isRefresh:Boolean=false
)

