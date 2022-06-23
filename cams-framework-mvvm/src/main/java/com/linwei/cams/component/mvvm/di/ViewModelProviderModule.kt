package com.linwei.cams.component.mvvm.di

import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMClassMapper
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMClassMapperInterface
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMProvider
import com.linwei.cams.component.mvvm.mvvm.viewmodel.VMProviderInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelProviderModule {

    @Binds
    abstract fun bindViewModelClassesMapper(mapper: VMClassMapper) : VMClassMapperInterface

    @Binds
    @Singleton
    abstract fun bindVMProvider(provider: VMProvider) : VMProviderInterface

}