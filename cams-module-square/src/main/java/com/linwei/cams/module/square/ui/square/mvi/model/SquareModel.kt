package com.linwei.cams.module.square.ui.square.mvi.model

import com.linwei.cams.framework.mvi.ktx.FetchStatus

data class MviViewState(
    val fetchStatus: FetchStatus = FetchStatus.NotFetched,
)

