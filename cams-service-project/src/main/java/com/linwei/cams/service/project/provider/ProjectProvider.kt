package com.linwei.cams.service.project.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.project.model.ProjectTreeBean

interface ProjectProvider : IProvider {

    suspend fun fetchProjectTreeData(): PageState<List<ProjectTreeBean>>

    suspend fun fetchProjectArticleListData(page: Int, cid: String): Page<CommonArticleBean>


}