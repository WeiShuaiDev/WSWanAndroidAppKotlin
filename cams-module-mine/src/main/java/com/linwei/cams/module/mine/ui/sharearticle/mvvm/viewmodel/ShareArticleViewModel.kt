package com.linwei.cams.module.mine.ui.sharearticle.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.component.common.ktx.isNullOrEmpty
import com.linwei.cams.component.mvvm.ktx.asLiveData
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.R
import com.linwei.cams.module.mine.ui.sharearticle.mvvm.model.ShareArticleModel
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShareArticleViewModel @Inject constructor(private val shareArticleModel: ShareArticleModel) :
    MvvmViewModel() {

    protected val _shareArticle: MutableLiveData<Any> =
        MutableLiveData<Any>()
    val shareArticle = _shareArticle.asLiveData()


    val titleValue: MutableLiveData<String> = MutableLiveData<String>()

    val linkValue: MutableLiveData<String> = MutableLiveData<String>()

    fun requestLogOut(title: String, link: String) {
        shareArticleModel.shareArticle(title, link, object : ResponseCallback<Any> {
            override fun onSuccess(data: Any) {
                _shareArticle.postValue(data)
            }

            override fun onFailed(errorMessage: ErrorMessage) {
                _showToast.postValue(errorMessage.message)
            }
        })
    }

    fun onShareClick(context: Context) {
        val title = titleValue.value
        val link = linkValue.value
        if (title.isNullOrEmpty()) {
            _showToast.postValue(context.getString(R.string.mine_input_title))
            return
        }
        if (link.isNullOrEmpty()) {
            _showToast.postValue(context.getString(R.string.mine_input_link))
            return
        }

        requestLogOut(title!!, link!!)
    }
}