package com.linwei.cams.module.square.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.square.http.ApiServiceWrap
import com.linwei.cams.service.square.SquareRouterTable
import com.linwei.cams.service.square.provider.SquareProvider
import dagger.MapKey
import javax.inject.Inject
@Route(path = SquareRouterTable.PATH_SERVICE_SQUARE)
class SquareProviderImpl @Inject constructor() : SquareProvider {

    private lateinit var mContext: Context

    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[SquareProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())

    override fun init(context: Context) {
        mContext = context
    }
}