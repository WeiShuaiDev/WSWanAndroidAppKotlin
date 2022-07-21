package com.linwei.cams.module.publis.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.module.publis.http.ApiServiceWrap
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.publis.provider.PublisProvider
import javax.inject.Inject

@Route(path = PublisRouterTable.PATH_SERVICE_PUBLIS)
class PublisProviderImpl @Inject constructor() : PublisProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }


    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[MineProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())
}