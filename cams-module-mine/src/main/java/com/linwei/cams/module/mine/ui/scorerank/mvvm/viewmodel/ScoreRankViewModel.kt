package com.linwei.cams.module.mine.ui.scorerank.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.ui.scorerank.mvvm.model.ScoreRankModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.mine.model.RankBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreRankViewModel @Inject constructor(private val scoreRankModel: ScoreRankModel) :
    MvvmViewModel() {

    private val _rankBeanPage: MutableLiveData<Page<RankBean>> =
        MutableLiveData<Page<RankBean>>()
    val rankBeanPage = _rankBeanPage.asLiveData()

    fun requestListScoreRankData(page: Int) {
        scoreRankModel.fetchListScoreRankData(page, object : ResponseCallback<Page<RankBean>> {

            override fun onSuccess(data: Page<RankBean>) {
                _rankBeanPage.postValue(data)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
                val isRefresh = page == 0
                _isRefresh.postValue(isRefresh)
            }
        })
    }
}