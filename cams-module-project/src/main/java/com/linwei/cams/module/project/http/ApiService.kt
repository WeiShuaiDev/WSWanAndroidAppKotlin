package com.linwei.cams.module.project.http

import com.linwei.cams.component.network.model.ApiResponse
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.project.model.ProjectTreeBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("project/tree/json")
    suspend fun getProjectTreeData(): ApiResponse<List<ProjectTreeBean>>

    @GET("project/list/{page}/json")
    suspend fun getProjectData(
        @Path("page") page: Int?,
        @Query("cid") cid: String?
    ): ApiResponse<Page<CommonArticleBean>>
}