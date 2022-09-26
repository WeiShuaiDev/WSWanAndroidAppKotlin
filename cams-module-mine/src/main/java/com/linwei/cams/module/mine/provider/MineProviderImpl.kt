package com.linwei.cams.module.mine.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.module.common.ktx.networks
import com.linwei.cams.module.mine.http.ApiServiceWrap
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.model.MyShareBean
import com.linwei.cams.service.mine.model.RankBean
import com.linwei.cams.service.mine.provider.MineProvider
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@Route(path = MineRouterTable.PATH_SERVICE_MINE)
class MineProviderImpl @Inject constructor() : MineProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[MineProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())

    override fun collectStatus(id: Int, callback: ResponseCallback<Any>) {
        collectApi(id).networks(callback)
    }

    override fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        unCollectApi(id).networks(callback)
    }

    override fun fetchListMyCollectData(
        page: Int,
        callback: ResponseCallback<Page<CommonArticleBean>>
    ) {
        listMyCollectApi(page).networks(callback)
    }

    override fun fetchIntegralData(callback: ResponseCallback<UserInfoBean>) {
        integralApi().networks(callback)
    }

    override fun fetchListIntegralData(page: Int, callback: ResponseCallback<Page<RankBean>>) {
        listIntegralApi(page).networks(callback)
    }

    override fun fetchListScoreRankData(page: Int, callback: ResponseCallback<Page<RankBean>>) {
        listScoreRankApi(page).networks(callback)
    }

    override fun fetchListMyShareData(page: Int, callback: ResponseCallback<MyShareBean>) {
        listMyShareApi(page).networks(callback)
    }

    override fun deleteArticle(id: Int, callback: ResponseCallback<Any>) {
        deleteArticleApi(id).networks(callback)
    }

    private fun collectApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.collect(id)

    private fun unCollectApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.unCollect(id)

    private fun listMyCollectApi(page: Int): Observable<ApiResponse<Page<CommonArticleBean>>> =
        mApiService.listMyCollectData(page)

    private fun integralApi(): Observable<ApiResponse<UserInfoBean>> =
        mApiService.getIntegralData()

    private fun listIntegralApi(page: Int): Observable<ApiResponse<Page<RankBean>>> =
        mApiService.listIntegralData(page)

    private fun listScoreRankApi(page: Int): Observable<ApiResponse<Page<RankBean>>> =
        mApiService.listScoreRankData(page)

    private fun listMyShareApi(page: Int): Observable<ApiResponse<MyShareBean>> =
        mApiService.listMyShareData(page)

    private fun deleteArticleApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.deleteArticle(id)

}