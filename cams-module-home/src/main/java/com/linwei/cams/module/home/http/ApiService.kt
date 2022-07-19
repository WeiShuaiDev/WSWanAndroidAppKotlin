package com.linwei.cams.module.home.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.BannerBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    /**
     * 获取文章列表
     */
    @GET("article/list/{page}/json")
    fun getArticleListData(@Path("page") page: Int?): Observable<ApiResponse<Page<CommonArticleBean>>>

    /**
     * 获取轮播图
     */
    @GET("banner/json")
    fun getBannerListData(): Observable<ApiResponse<List<BannerBean>>>

}