package com.linwei.camsmodular.ui.welcome.model

import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.service.project.model.ProjectTreeBean

data class MviViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,

    val projectTreeList: List<ProjectTreeBean> = emptyList(),
)

