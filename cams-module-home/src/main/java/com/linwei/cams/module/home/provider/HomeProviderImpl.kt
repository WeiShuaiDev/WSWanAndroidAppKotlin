package com.linwei.cams.module.home.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.callback.ErrorConsumer
import com.linwei.cams.component.network.callback.RxJavaCallback
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.ktx.execute
import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.component.network.transformer.ResponseTransformer
import com.linwei.cams.module.home.http.ApiServiceWrap
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.provider.HomeProvider
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

@Route(path = HomeRouterTable.PATH_SERVICE_HOME)
open class HomeProviderImpl @Inject constructor() : HomeProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[HomeProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())

    override fun fetchArticleListData(
        page: Int,
        callback: ResponseCallback<Page<CommonArticleBean>>
    ) {
        homeApi(page)
            .execute(object : RxJavaCallback<Page<CommonArticleBean>>() {

                override fun onSuccess(code: Int?, data: Page<CommonArticleBean>) {
                    super.onSuccess(code, data)
                    callback.onSuccess(data)
                }

                override fun onFailure(code: Int?, message: String?) {
                    super.onFailure(code, message)
                    callback.onFailed(ErrorMessage(code, message))
                }
            })
    }

    private fun homeApi(page: Int): Observable<ApiResponse<Page<CommonArticleBean>>> =
        mApiService.getArticleListData(page)

    override fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>) {
        bannerApi()
            .compose(ResponseTransformer.obtain())
            .subscribe({ data ->
                callback.onSuccess(data)
            }, object : ErrorConsumer() {
                override fun error(e: ApiException) {
                    callback.onFailed(ErrorMessage(e.code, e.displayMessage))
                }
            })
    }

     private fun bannerApi(): Observable<ApiResponse<List<BannerBean>>> =
         mApiService.getBannerListData()

}