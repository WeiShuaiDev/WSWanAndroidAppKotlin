package com.linwei.cams.module.project.ui.project.mvi.intent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.linwei.cams.component.cache.utils.mmkv.AppDataProvided
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.component.common.ktx.isNotNullOrSize
import com.linwei.cams.component.network.ktx.commonCatch
import com.linwei.cams.framework.mvi.ktx.FetchStatus
import com.linwei.cams.framework.mvi.ktx.asLiveData
import com.linwei.cams.framework.mvi.ktx.setState
import com.linwei.cams.framework.mvi.mvi.intent.MviViewModel
import com.linwei.cams.framework.mvi.mvi.intent.StatusCode
import com.linwei.cams.framework.mvi.mvi.model.MviViewEvent
import com.linwei.cams.module.project.provider.ProjectProviderImpl
import com.linwei.cams.module.project.ui.project.mvi.model.MviViewState
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.mine.provider.MineProvider
import com.linwei.cams.service.mine.provider.MineProviderHelper
import com.linwei.cams.service.project.model.ProjectTreeBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor() : MviViewModel() {

    private val mProjectProvider: ProjectProviderImpl = ProjectProviderImpl()

    private val mMineProvider: MineProvider? = MineProviderHelper.getMineProvider()

    private val _viewStates: MutableLiveData<MviViewState> = MutableLiveData(MviViewState())
    val viewState = _viewStates.asLiveData()

    /**
     * 增加文章收藏
     */
    fun requestCollectStatus(id: Int) {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }

        mMineProvider?.collectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _viewStates.setState {
                    copy(
                        collectStatus = true,
                        fetchStatus = FetchStatus.Fetched
                    )
                }
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _viewStates.setState {
                    copy(fetchStatus = FetchStatus.NotFetched)
                }

                errorMessage.message?.takeIf { it.isNotEmpty() }?.apply {
                    postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, this))
                }
            }
        })
    }

    /**
     * 取消文章收藏
     */
    fun requestUnCollectStatus(id: Int) {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }

        mMineProvider?.unCollectStatus(id, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _viewStates.setState {
                    copy(
                        collectStatus = false,
                        fetchStatus = FetchStatus.Fetched
                    )
                }
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _viewStates.setState {
                    copy(fetchStatus = FetchStatus.NotFetched)
                }

                errorMessage.message?.takeIf { it.isNotEmpty() }?.apply {
                    postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, this))
                }
            }
        })
    }

    /**
     * 获取项目树数据
     */
    fun requestProjectTreeData() {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }

        viewModelScope.launch {
            //MMkv中读取projectTreeBean数据
            val projectTreeList: List<ProjectTreeBean> = AppDataProvided().getProjectTree()
            if (projectTreeList.isNullOrEmpty()) {
                when (val result = mProjectProvider.fetchProjectTreeData()) {
                    is PageState.Error -> {
                        _viewStates.setState {
                            copy(fetchStatus = FetchStatus.NotFetched)
                        }
                        postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, result.message))
                    }
                    is PageState.Success -> {
                        //projectTreeBean保存数据到MMkv
                        if (result.data.isNotNullOrSize()) {
                            AppDataProvided().saveProjectTree(result.data)
                        }
                        _viewStates.setState {
                            copy(fetchStatus = FetchStatus.Fetched, projectTreeList = result.data)
                        }
                    }
                }
            } else {
                _viewStates.setState {
                    copy(fetchStatus = FetchStatus.Fetched, projectTreeList = projectTreeList)
                }
            }
        }
    }

    /**
     * 获取项目树详情数据
     */
    fun requestProjectData(page: Int, cid: String) {
        viewModelScope.launch {
            val isRefresh = page == 0
            flow {
                emit(mProjectProvider.fetchProjectData(page, cid))
            }.onStart {
                _viewStates.setState { copy(fetchStatus = FetchStatus.Fetching) }
            }.onEach {
                _viewStates.setState {
                    copy(
                        fetchStatus = FetchStatus.Fetched,
                        commonArticlePage = it
                    )
                }
            }.commonCatch {
                _viewStates.setState {
                    copy(
                        fetchStatus = FetchStatus.NotFetched,
                        isRefresh = isRefresh
                    )
                }
            }.collect()
        }
    }
}