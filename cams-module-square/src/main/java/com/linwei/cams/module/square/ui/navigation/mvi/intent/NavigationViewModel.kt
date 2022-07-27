package com.linwei.cams.module.square.ui.square.mvi.intent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.asLiveData
import com.linwei.cams.framework.mvi.ktx.setState
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.module.square.provider.SquareProviderImpl
import com.linwei.cams.module.square.ui.square.mvi.model.NavigationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : MviViewModel() {

    private var mSquareProvider: SquareProviderImpl = SquareProviderImpl()

    private val _viewStates: MutableLiveData<NavigationViewState> = MutableLiveData(NavigationViewState())
    val viewState = _viewStates.asLiveData()

    fun requestSquareNavisData() {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }

        viewModelScope.launch {
            when (val result = mSquareProvider.fetchSquareNavisData()) {
                is PageState.Error -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.NotFetched)
                    }
                    postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, result.message))
                }
                is PageState.Success -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.Fetched, squareNavisList = result.data)
                    }
                }
            }
        }
    }

}