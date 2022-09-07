package com.linwei.cams.service.mine.model

import com.linwei.cams.service.base.model.CommonArticleBean
import com.linwei.cams.service.base.model.Page
import com.linwei.cams.service.base.model.UserInfoBean

/**
 * 我的分享实体类
 */
data class MyShareBean(val coinInfo: UserInfoBean?, val shareArticles: Page<CommonArticleBean>?)