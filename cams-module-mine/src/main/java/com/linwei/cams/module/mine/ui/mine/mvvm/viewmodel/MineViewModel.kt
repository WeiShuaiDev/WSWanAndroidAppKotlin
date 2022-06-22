package com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel

import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.mine.ui.mine.mvvm.model.MineModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor() : MvvmViewModel() {

    private val mMineModel: MineModel = MineModel()

}