package com.linwei.cams.module.home.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.home.model.BannerBean
import com.linwei.cams.service.home.model.SearchBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    /**
     * 热门搜索
     */
    @POST("hotkey/json")
    fun getHotSearchData(): Observable<ApiResponse<List<SearchBean.SearchDetailsBean>>>

    /**
     * 搜索内容
     */
    @POST("article/query/{page}/json")
    fun getSearchData(@Path("page") pageNo: Int, @Query("k") k: String?): Observable<ApiResponse<Page<CommonArticleBean>>>

}