package com.linwei.cams.module.main.di

import com.linwei.cams.component.mvvm.di.ViewModelKey
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.module.main.ui.main.mvvm.model.MainModel
import com.linwei.cams.module.main.ui.main.mvvm.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
object DiMainModule {


    /**
     * Main模块的[MainViewModel]依赖提供方法
     *
     * @return MainViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun providePublisViewModel(mainModel: MainModel): MvvmViewModel =
        MainViewModel(mainModel)

}