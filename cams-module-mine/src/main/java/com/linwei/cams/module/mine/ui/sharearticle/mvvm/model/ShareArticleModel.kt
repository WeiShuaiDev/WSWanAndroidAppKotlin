package com.linwei.cams.module.mine.ui.sharearticle.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import com.linwei.cams.service.base.callback.ResponseCallback
import javax.inject.Inject

class ShareArticleModel @Inject constructor(private val mineProvider: MineProviderImpl) :
    MvvmModel() {

    fun shareArticle(title: String, link: String, callback: ResponseCallback<Any>) {
        mineProvider.shareArticle(title, link, callback)
    }
}