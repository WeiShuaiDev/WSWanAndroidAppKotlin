package com.linwei.cams.module.square.ui.square.mvi.intent

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.framework.mvi.ktx.asLiveData
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.module.square.provider.SquareProviderImpl
import com.linwei.cams.module.square.ui.square.mvi.model.MviViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SquareViewModel @Inject constructor(private val squareProvider: SquareProviderImpl) :
    MviViewModel() {

    private val _viewStates: MutableLiveData<MviViewState> = MutableLiveData(MviViewState())
    val viewState = _viewStates.asLiveData()

}