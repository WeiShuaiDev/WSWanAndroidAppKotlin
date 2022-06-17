package com.linwei.cams.module.main.ui.main.viewmodel

import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.main.ui.main.model.MainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainModel: MainModel): MvvmViewModel() {




}