package com.linwei.cams.module.mine.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean
import com.linwei.cams.service.mine.model.RankBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

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
    fun listScoreRank(@Path("page") page: Int): Observable<ApiResponse<Page<RankBean>>>

    /**
     * 获取个人积分
     */
    @GET("lg/coin/userinfo/json")
    fun getIntegralData(): Observable<ApiResponse<UserInfoBean>>

    /**
     * 获取个人积分列表
     */
    @GET("lg/coin/list/{page}/json")
    fun listIntegral(@Path("page") page: Int): Observable<ApiResponse<Page<RankBean>>>

}