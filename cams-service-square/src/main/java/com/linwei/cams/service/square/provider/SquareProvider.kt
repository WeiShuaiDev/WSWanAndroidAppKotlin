package com.linwei.cams.service.square.provider

import com.alibaba.android.arouter.facade.template.IProvider
import com.linwei.cams.component.common.global.PageState
import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.square.model.SquareTreeBean

interface SquareProvider : IProvider {

    suspend fun fetchSquareTreeData(): PageState<List<SquareTreeBean>>

    suspend fun fetchSquareNavisData(): PageState<List<SquareTreeBean>>

    suspend fun fetchSquareTreeArticleListData(
        page: Int,
        id: String
    ): PageState<Page<CommonArticleBean>>


}