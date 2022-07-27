package com.linwei.cams.module.square.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.module.square.http.ApiServiceWrap
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.square.SquareRouterTable
import com.linwei.cams.service.square.model.SquareTreeBean
import com.linwei.cams.service.square.provider.SquareProvider
import java.lang.Exception
import javax.inject.Inject

@Route(path = SquareRouterTable.PATH_SERVICE_SQUARE)
class SquareProviderImpl @Inject constructor() : SquareProvider {

    private lateinit var mContext: Context

    override fun init(context: Context) {
        mContext = context
    }

    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[SquareProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())

    override suspend fun fetchSquareTreeData(): PageState<List<SquareTreeBean>> {
        val squareTreeData = try {
            mApiService.getSquareTreeData()
        } catch (e: Exception) {
            return PageState.Error(e)
        }

        squareTreeData.takeIf { it.errorCode == ApiConstants.REQUEST_SUCCESS }?.run {
            this.data?.let {
                return PageState.Success(it)
            } ?: run {
                return PageState.Error(errorMsg)
            }
        }
        return PageState.Error(squareTreeData.errorMsg)
    }

    override suspend fun fetchSquareNavisData(): PageState<List<SquareTreeBean>> {
        val squareNaviData = try {
            mApiService.getSquareNaviData()
        } catch (e: Exception) {
            return PageState.Error(e)
        }

        squareNaviData.takeIf { it.errorCode == ApiConstants.REQUEST_SUCCESS }?.run {
            this.data?.let {
                return PageState.Success(it)
            } ?: run {
                return PageState.Error(errorMsg)
            }
        }
        return PageState.Error(squareNaviData.errorMsg)
    }

    override suspend fun fetchSquareTreeArticleListData(
        page: Int,
        id: String
    ): PageState<Page<CommonArticleBean>> {
        val squareTreeArticleListData = try {
            mApiService.getSquareTreeArticleListData(page, id)
        } catch (e: Exception) {
            return PageState.Error(e)
        }

        squareTreeArticleListData.takeIf { it.errorCode == ApiConstants.REQUEST_SUCCESS }?.run {
            this.data?.let {
                return PageState.Success(it)
            } ?: run {
                return PageState.Error(errorMsg)
            }
        }
        return PageState.Error(squareTreeArticleListData.errorMsg)
    }
}