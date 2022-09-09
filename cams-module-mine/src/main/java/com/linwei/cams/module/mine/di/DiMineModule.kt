package com.linwei.cams.module.mine.di

import com.linwei.cams.component.mvvm.di.ViewModelKey
import com.linwei.cams.component.mvvm.mvvm.viewmodel.MvvmViewModel
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.mine.http.ApiService
import com.linwei.cams.module.mine.http.ApiServiceWrap
import com.linwei.cams.module.mine.ui.mine.mvvm.model.MineModel
import com.linwei.cams.module.mine.ui.mine.mvvm.viewmodel.MineViewModel
import com.linwei.cams.module.mine.ui.mycollect.mvvm.model.MyCollectModel
import com.linwei.cams.module.mine.ui.mycollect.mvvm.viewmodel.MyCollectViewModel
import com.linwei.cams.module.mine.ui.myscore.mvvm.model.MyScoreModel
import com.linwei.cams.module.mine.ui.myscore.mvvm.viewmodel.MyScoreViewModel
import com.linwei.cams.module.mine.ui.myshare.mvvm.model.MyShareModel
import com.linwei.cams.module.mine.ui.myshare.mvvm.viewmodel.MyShareViewModel
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

    /**
     * Mine模块的[MyScoreViewModel]依赖提供方法
     *
     * @return MyScoreViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(MyScoreViewModel::class)
    fun provideMyScoreViewModel(myScoreModel: MyScoreModel): MvvmViewModel =
        MyScoreViewModel(myScoreModel)

    /**
     * Mine模块的[MyCollectViewModel]依赖提供方法
     *
     * @return MyCollectViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(MyCollectViewModel::class)
    fun provideMyCollectViewModel(myCollectModel: MyCollectModel): MvvmViewModel =
        MyCollectViewModel(myCollectModel)

    /**
     * Mine模块的[MyShareViewModel]依赖提供方法
     *
     * @return MyShareViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(MyShareViewModel::class)
    fun provideMyShareViewModel(myShareModel: MyShareModel): MvvmViewModel =
        MyShareViewModel(myShareModel)

}