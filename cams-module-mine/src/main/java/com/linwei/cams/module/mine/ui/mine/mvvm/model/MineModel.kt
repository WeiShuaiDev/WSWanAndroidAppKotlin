package com.linwei.cams.module.mine.ui.mine.mvvm.model

import com.linwei.cams.component.mvvm.mvvm.model.MvvmModel
import com.linwei.cams.module.mine.provider.MineProviderImpl
import javax.inject.Inject

class MineModel @Inject constructor() : MvvmModel() {

    @Inject
    lateinit var mMineProvider: MineProviderImpl


}