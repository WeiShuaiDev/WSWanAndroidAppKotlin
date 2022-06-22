package com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel

import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.publis.ui.publis.mvvm.model.PublisModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PublisViewModel @Inject constructor() : MvvmViewModel() {

    private val mPublisModel: PublisModel = PublisModel()

}