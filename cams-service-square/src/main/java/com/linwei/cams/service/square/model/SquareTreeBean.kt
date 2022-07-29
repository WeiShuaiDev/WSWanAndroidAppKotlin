package com.linwei.cams.service.square.model

import com.linwei.cams.service.base.model.CommonArticleBean

data class SquareTreeBean(
    val name: String?,
    val courseId: String?,
    val id: String?,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: List<ChildrenBean>?,
    val cid: String?,
    val articles: List<CommonArticleBean>?
) {

    data class ChildrenBean(
        val courseId: String?,
        val id: String?,
        val name: String?,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int,
        val children: List<Any>?
    )
}