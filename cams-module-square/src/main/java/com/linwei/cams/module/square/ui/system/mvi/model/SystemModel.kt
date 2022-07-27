package com.linwei.cams.module.square.ui.square.mvi.model

import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.service.square.model.SquareTreeBean

data class SystemViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,

    val squareTreeList: List<SquareTreeBean> = emptyList(),

)

