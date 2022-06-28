package com.linwei.cams.module.mine.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.callback.ErrorConsumer
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.component.network.transformer.ResponseTransformer
import com.linwei.cams.module.mine.http.ApiService
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.model.HomeBean
import com.linwei.cams.service.mine.MineRouterTable
import com.linwei.cams.service.mine.provider.MineProvider
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@Route(path = MineRouterTable.PATH_SERVICE_MINE)
class MineProviderImpl @Inject constructor(private val apiService: ApiService) : MineProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    override fun collectStatus(id: String, callback: ResponseCallback<Any>) {
        collectApi(id)
            .compose(ResponseTransformer.obtain())
            .subscribe({ data ->
                callback.onSuccess(data)
            }, object : ErrorConsumer() {
                override fun error(e: ApiException) {
                    callback.onFailed(ErrorMessage(e.code, e.message))
                }
            })
    }

    fun collectApi(id: String): Observable<ApiResponse<Any>> =
        apiService.collect(id)

    override fun unCollectStatus(id: String, callback: ResponseCallback<Any>) {
        unCollectApi(id)
            .compose(ResponseTransformer.obtain())
            .subscribe({ data ->
                callback.onSuccess(data)
            }, object : ErrorConsumer() {
                override fun error(e: ApiException) {
                    callback.onFailed(ErrorMessage(e.code, e.message))
                }
            })
    }

    fun unCollectApi(id: String): Observable<ApiResponse<Any>> =
        apiService.unCollect(id)


}