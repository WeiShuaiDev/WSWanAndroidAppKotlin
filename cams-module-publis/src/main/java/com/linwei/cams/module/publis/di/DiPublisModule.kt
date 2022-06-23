package com.linwei.cams.module.publis.di

import com.linwei.cams.component.mvvm.di.ViewModelKey
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.publis.http.ApiService
import com.linwei.cams.module.publis.http.ApiServiceWrap
import com.linwei.cams.module.publis.ui.publis.mvvm.model.PublisModel
import com.linwei.cams.module.publis.ui.publis.mvvm.viewmodel.PublisViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import kotlin.reflect.KClass

@Module
@InstallIn(SingletonComponent::class)
object DiPublisModule {

    /**
     * Publis模块的[ApiService]依赖提供方法
     *
     * @param apiServiceWrap ApiServiceWrap
     * @return ApiService
     */
    @Provides
    @Singleton
    fun providePublisApiService(apiServiceWrap: ApiServiceWrap): ApiService {
        return ApiClient.getInstance().getService(apiServiceWrap)
    }

    /**
     * Publis模块的[PublisViewModel]依赖提供方法
     *
     * @return PublisViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(PublisViewModel::class)
    fun providePublisViewModel(publisModel: PublisModel): MvvmViewModel =
        PublisViewModel(publisModel)

}