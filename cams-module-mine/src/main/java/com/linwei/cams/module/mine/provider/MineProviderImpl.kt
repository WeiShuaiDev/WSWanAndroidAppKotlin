package com.linwei.cams.module.mine.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.module.common.ktx.networks
import com.linwei.cams.module.mine.http.ApiServiceWrap
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.MineRouterTable
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

    override fun fetchIntegralData(callback: ResponseCallback<UserInfoBean>) {
        integralApi().networks(callback)
    }

    override fun fetchListIntegralData(page: Int, callback: ResponseCallback<Page<RankBean>>) {
        listIntegralApi(page).networks(callback)
    }

    fun collectApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.collect(id)

    fun unCollectApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.unCollect(id)

    fun integralApi(): Observable<ApiResponse<UserInfoBean>> =
        mApiService.getIntegralData()

    fun listIntegralApi(page: Int): Observable<ApiResponse<Page<RankBean>>> =
        mApiService.listIntegralData(page)
}