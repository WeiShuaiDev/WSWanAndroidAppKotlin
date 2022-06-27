package com.linwei.cams.module.home.provider

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.callback.ErrorConsumer
import com.linwei.cams.component.network.callback.RxJavaCallback
import com.linwei.cams.component.network.exception.ApiException
import com.linwei.cams.component.network.ktx.execute
import com.linwei.cams.component.network.transformer.ResponseTransformer
import com.linwei.cams.module.home.http.ApiService
import com.linwei.cams.module.home.http.ApiServiceWrap
import com.linwei.cams.service.base.ErrorMessage
import com.linwei.cams.service.base.callback.ResponseCallback
import com.linwei.cams.service.home.HomeRouterTable
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.HomeBean
import com.linwei.cams.service.home.provider.HomeProvider
import javax.inject.Inject


@Route(path = HomeRouterTable.PATH_SERVICE_HOME)
class HomeProviderImpl @Inject constructor(private val apiService: ApiService) : HomeProvider {
    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    override fun fetchHomeData(
        page: Int,
        callback: ResponseCallback<HomeBean>
    ) {
        apiService.getArticleListData(1)
            .execute(object : RxJavaCallback<HomeBean>() {

                override fun onSuccess(code: Int?, data: HomeBean) {
                    super.onSuccess(code, data)
                    callback.onSuccess(data)
                }

                override fun onFailure(code: Int?, message: String?) {
                    super.onFailure(code, message)
                    callback.onFailed(ErrorMessage(code, message))
                }
            })
    }

    override fun fetchBannerData(callback: ResponseCallback<List<BannerBean>>) {
        apiService.getBannerListData()
            .compose(ResponseTransformer.obtain())
            .subscribe({ data ->
                callback.onSuccess(data)
            }, object : ErrorConsumer() {
                override fun error(e: ApiException) {
                    callback.onFailed(ErrorMessage(e.code, e.message))
                }
            })
    }

}