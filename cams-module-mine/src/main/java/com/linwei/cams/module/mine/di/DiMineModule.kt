package com.linwei.cams.module.mine.di

import com.linwei.cams.component.mvvm.di.ViewModelKey
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.mine.http.ApiService
import com.linwei.cams.module.mine.http.ApiServiceWrap
import com.linwei.cams.module.mine.ui.mine.mvvm.model.MineModel
import com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel.MineViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiMineModule {

    /**
     * Mine模块的[ApiService]依赖提供方法
     *
     * @param apiServiceWrap ApiServiceWrap
     * @return ApiService
     */
    @Provides
    @Singleton
    fun provideMineApiService(apiServiceWrap: ApiServiceWrap): ApiService {
        return ApiClient.getInstance().getService(apiServiceWrap)
    }

    /**
     * Mine模块的[MineViewModel]依赖提供方法
     *
     * @return MineViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    fun provideMineViewModel(mineModel: MineModel): MvvmViewModel =
        MineViewModel(mineModel)

}