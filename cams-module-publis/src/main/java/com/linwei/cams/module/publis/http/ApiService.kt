package com.linwei.cams.module.publis.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.publis.model.PublisAuthorBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    /**
     * 公众号作业列表
     */
    @GET("wxarticle/chapters/json")
    fun getPublicAuthorData(): Observable<ApiResponse<List<PublisAuthorBean>>>

    /**
     * 获取知识体系下文章列表
     */
    @GET("article/list/{page}/json")
    fun getPublicArticleListData(
        @Path("page") page: Int?,
        @Query("cid") id: String?
    ): Observable<ApiResponse<Page<CommonArticleBean>>>

}