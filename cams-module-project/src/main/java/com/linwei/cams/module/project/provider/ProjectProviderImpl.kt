package com.linwei.cams.module.project.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.component.network.ApiClient
import com.linwei.cams.component.network.ApiConstants
import com.linwei.cams.module.project.http.ApiServiceWrap
import com.linwei.cams.service.project.ProjectRouterTable
import com.linwei.cams.service.project.model.ProjectTreeBean
import com.linwei.cams.service.project.model.ProjectTreeDetailsBean
import com.linwei.cams.service.project.provider.ProjectProvider
import java.lang.Exception
import javax.inject.Inject

@Route(path = ProjectRouterTable.PATH_SERVICE_PROJECT)
class ProjectProviderImpl @Inject constructor() : ProjectProvider {

    private lateinit var mContext: Context

    /**
     * TODO:这里发现一问题，ApiService使用Hint注入，导致跨模块引用 `ApiService`还没初始化。
     *      这样违背之前设置原则.所以只能妥协。毕竟[ProjectProviderImpl]类是跨模块共享
     */
    private val mApiService = ApiClient.getInstance().getService(ApiServiceWrap())

    override fun init(context: Context) {
        mContext = context
    }

    override suspend fun fetchProjectTreeData(): PageState<List<ProjectTreeBean>> {
        val projectTreeData = try {
            mApiService.getProjectTreeData()
        } catch (e: Exception) {
            return PageState.Error(e)
        }

        projectTreeData.takeIf { it.errorCode == ApiConstants.REQUEST_SUCCESS }?.apply {
            this.data?.let {
                return PageState.Success(it)
            } ?: run {
                return PageState.Error(errorMsg)
            }
        }
        return PageState.Error(projectTreeData.errorMsg)
    }

    override suspend fun fetchProjectTreeDetailsData(): ProjectTreeDetailsBean {
        val projectTreeDetailsData =
            mApiService.getProjectTreeDetailsData()

        projectTreeDetailsData.takeIf { it.errorCode == ApiConstants.REQUEST_SUCCESS }?.apply {
            this.data?.let {
                return it
            } ?: run {
                throw NullPointerException(errorMsg)
            }
        }
        throw NullPointerException(projectTreeDetailsData.errorMsg)
    }
}