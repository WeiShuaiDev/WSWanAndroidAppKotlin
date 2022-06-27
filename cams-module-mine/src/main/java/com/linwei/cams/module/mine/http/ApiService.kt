package com.linwei.cams.module.mine.http

import com.linwei.cams.component.network.model.ApiResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    /**
     * 增加收藏
     */
    @POST("lg/collect/{id}/json")
    fun collect(@Path("id") id: String): Observable<ApiResponse<Any>>

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: String): Observable<ApiResponse<Any>>

}