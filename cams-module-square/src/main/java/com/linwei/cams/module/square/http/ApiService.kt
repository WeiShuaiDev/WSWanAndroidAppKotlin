package com.linwei.cams.module.square.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.square.model.SquareTreeBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * 获取体系
     */
    @GET("tree/json")
    suspend fun getSquareTreeData(): ApiResponse<List<SquareTreeBean>>

    /**
     * 获取导航
     */
    @GET("navi/json")
    suspend fun getSquareNaviData(): ApiResponse<List<SquareTreeBean>>

    /**
     * 获取知识体系下文章列表
     */
    @GET("article/list/{page}/json")
    suspend fun getSquareTreeArticleListData(
        @Path("page") page: Int?,
        @Query("cid") id: String?
    ): ApiResponse<Page<CommonArticleBean>>

}