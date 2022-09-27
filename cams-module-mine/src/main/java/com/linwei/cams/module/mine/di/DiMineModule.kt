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
import com.linwei.cams.module.mine.ui.scorerank.mvvm.model.ScoreRankModel
import com.linwei.cams.module.mine.ui.scorerank.mvvm.viewmodel.ScoreRankViewModel
import com.linwei.cams.module.mine.ui.setting.mvvm.model.SettingModel
import com.linwei.cams.module.mine.ui.setting.mvvm.viewmodel.SettingViewModel
import com.linwei.cams.module.mine.ui.sharearticle.mvvm.model.ShareArticleModel
import com.linwei.cams.module.mine.ui.sharearticle.mvvm.viewmodel.ShareArticleViewModel
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

    /**
     * Mine模块的[SettingViewModel]依赖提供方法
     *
     * @return SettingViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    fun provideSettingViewModel(settingModel: SettingModel): MvvmViewModel =
        SettingViewModel(settingModel)

    /**
     * Mine模块的[ScoreRankViewModel]依赖提供方法
     *
     * @return ScoreRankViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(ScoreRankViewModel::class)
    fun provideScoreRankViewModel(scoreRankModel: ScoreRankModel): MvvmViewModel =
        ScoreRankViewModel(scoreRankModel)

    /**
     * Mine模块的[ShareArticleViewModel]依赖提供方法
     *
     * @return ShareArticleViewModel
     */
    @Provides
    @IntoMap
    @ViewModelKey(ShareArticleViewModel::class)
    fun provideShareArticleViewModel(shareArticleModel: ShareArticleModel): MvvmViewModel =
        ShareArticleViewModel(shareArticleModel)

}