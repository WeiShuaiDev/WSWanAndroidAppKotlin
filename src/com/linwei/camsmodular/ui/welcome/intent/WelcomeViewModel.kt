package com.linwei.camsmodular.ui.welcome.intent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.asLiveData
import com.linwei.cams.framework.mvi.ktx.setState
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.service.project.provider.ProjectProviderHelper
import com.linwei.camsmodular.ui.welcome.model.MviViewState
import kotlinx.coroutines.launch

class WelcomeViewModel : MviViewModel() {

    private val _viewStates: MutableLiveData<MviViewState> = MutableLiveData(MviViewState())
    val viewState = _viewStates.asLiveData()

    private val projectProvider = ProjectProviderHelper.getProjectProvider()

    /**
     * 获取项目树数据
     */
    fun fetchProjectTreeData() {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }
        viewModelScope.launch {
            when (val result = projectProvider?.fetchProjectTreeData()) {
                is PageState.Error -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.NotFetched)
                    }
                    postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, result.message))
                }
                is PageState.Success -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.Fetched, projectTreeList = result.data)
                    }
                }
            }
        }
    }

}