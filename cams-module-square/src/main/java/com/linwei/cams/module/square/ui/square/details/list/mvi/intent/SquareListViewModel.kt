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
import com.linwei.cams.module.square.ui.square.mvi.model.SquareListViewState
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.mine.provider.MineProvider
import com.linwei.cams.service.mine.provider.MineProviderHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SquareListViewModel @Inject constructor() : MviViewModel() {

    private var mSquareProvider: SquareProviderImpl = SquareProviderImpl()

    private val mMineProvider: MineProvider? = MineProviderHelper.getMineProvider()

    private val _viewStates: MutableLiveData<SquareListViewState> =
        MutableLiveData(SquareListViewState())
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

    fun requestSquareTreeArticleListData(page: Int, id: String) {
        _viewStates.setState {
            copy(fetchStatus = FetchStatus.Fetching)
        }
        viewModelScope.launch {
            val isRefresh = page == 0
            when (val result = mSquareProvider.fetchSquareTreeArticleListData(page, id)) {
                is PageState.Error -> {
                    _viewStates.setState {
                        copy(fetchStatus = FetchStatus.NotFetched, isRefresh = isRefresh)
                    }
                    postUpdateEvents(MviViewEvent(StatusCode.SHOW_TOAST, result.message))
                }
                is PageState.Success -> {
                    _viewStates.setState {
                        copy(
                            fetchStatus = FetchStatus.Fetched,
                            commonArticlePage = result.data
                        )
                    }
                }
            }
        }
    }

}