package com.linwei.cams.module.project.ui.project.mvi.model

import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.project.model.ProjectTreeBean

data class MviViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,

    val projectTreeList: List<ProjectTreeBean> = emptyList(),

    val commonArticlePage: Page<CommonArticleBean> = Page(),

    val collectStatus: Boolean = false,

    val isRefresh:Boolean=false
)

