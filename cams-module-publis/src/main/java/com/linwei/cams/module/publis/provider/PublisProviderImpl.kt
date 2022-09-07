package com.linwei.cams.module.publis.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.callback.ErrorConsumer
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.component.network.transformer.ResponseTransformer
import com.linwei.cams.module.common.ktx.networks
import com.linwei.cams.module.publis.http.ApiServiceWrap
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.PublisRouterTable
import com.linwei.cams.service.publis.model.PublisAuthorBean
import com.linwei.cams.service.publis.provider.PublisProvider
import io.reactivex.rxjava3.core.Observable
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

    override fun fetchPublicAuthorData(
        callback: ResponseCallback<List<PublisAuthorBean>>
    ) {
        publicAuthorApi().networks(callback)
    }

    private fun publicAuthorApi(): Observable<ApiResponse<List<PublisAuthorBean>>> =
        mApiService.getPublicAuthorData()

    override fun fetchPublicArticleListData(
        page: Int,
        id: String?,
        callback: ResponseCallback<Page<CommonArticleBean>>
    ) {
        publicAuthorApi(page, id).networks(callback)
    }

    private fun publicAuthorApi(
        page: Int,
        id: String?
    ): Observable<ApiResponse<Page<CommonArticleBean>>> =
        mApiService.getPublicArticleListData(page, id)

}