package com.linwei.cams.module.square.di

import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.square.http.ApiService
import com.linwei.cams.module.square.http.ApiServiceWrap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiSquareModule {

    /**
     * Square模块的[ApiService]依赖提供方法
     *
     * @param apiServiceWrap ApiServiceWrap
     * @return ApiService
     */
    @Provides
    @Singleton
    fun provideProjectApiService(apiServiceWrap: ApiServiceWrap): ApiService {
        return ApiClient.getInstance().getService(apiServiceWrap)
    }
}