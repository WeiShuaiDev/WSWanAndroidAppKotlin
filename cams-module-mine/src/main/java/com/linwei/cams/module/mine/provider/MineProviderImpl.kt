package com.linwei.cams.module.mine.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.callback.ErrorConsumer
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.component.network.transformer.ResponseTransformer
import com.linwei.cams.module.mine.http.ApiServiceWrap
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.mine.MineRouterTable
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
        collectApi(id)
            .compose(ResponseTransformer.obtain())
            .subscribe({ data ->
                callback.onSuccess(data)
            }, object : ErrorConsumer() {
                override fun error(e: ApiException) {
                    callback.onFailed(ErrorMessage(e.code, e.displayMessage))
                }
            })
    }

    fun collectApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.collect(id)

    override fun unCollectStatus(id: Int, callback: ResponseCallback<Any>) {
        unCollectApi(id)
            .compose(ResponseTransformer.obtain())
            .subscribe({ data ->
                callback.onSuccess(data)
            }, object : ErrorConsumer() {
                override fun error(e: ApiException) {
                    callback.onFailed(ErrorMessage(e.code, e.displayMessage))
                }
            })
    }

    fun unCollectApi(id: Int): Observable<ApiResponse<Any>> =
        mApiService.unCollect(id)


}