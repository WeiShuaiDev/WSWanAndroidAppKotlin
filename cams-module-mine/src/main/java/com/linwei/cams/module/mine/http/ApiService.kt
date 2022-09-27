package com.linwei.cams.module.mine.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.model.MyShareBean
import com.linwei.cams.service.mine.model.RankBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    /**
     * 我的收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    fun listMyCollectData(@Path("page") page: Int): Observable<ApiResponse<Page<CommonArticleBean>>>

    /**
     * 增加收藏
     */
    @POST("lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Observable<ApiResponse<Any>>

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: Int): Observable<ApiResponse<Any>>

    /**
     * 积分排行榜
     */
    @GET("coin/rank/{page}/json")
    fun listScoreRankData(@Path("page") page: Int): Observable<ApiResponse<Page<RankBean>>>

    /**
     * 获取个人积分
     */
    @GET("lg/coin/userinfo/json")
    fun getIntegralData(): Observable<ApiResponse<UserInfoBean>>

    /**
     * 获取个人积分列表
     */
    @GET("lg/coin/list/{page}/json")
    fun listIntegralData(@Path("page") page: Int): Observable<ApiResponse<Page<RankBean>>>

    /**
     * 我的分享
     */
    @GET("user/lg/private_articles/{page}/json")
    fun listMyShareData(@Path("page") page: Int): Observable<ApiResponse<MyShareBean>>

    /**
     * 刪除文章
     */
    @POST("lg/user_article/delete/{id}/json")
    fun deleteArticle(@Path("id") id: Int): Observable<ApiResponse<Any>>

    /**
     * 分享文章
     */
    @POST("lg/user_article/add/json")
    fun shareArticle(
        @Query("title") title: String,
        @Query("link") link: String
    ): Observable<ApiResponse<Any>>
}