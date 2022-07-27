package com.linwei.cams.service.square.model

import com.linwei.cams.service.base.model.CommonArticleBean

data class SquareTreeBean(
    var name: String?,
    var courseId: String?,
    var id: String?,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int,
    var children: List<ChildrenBean>?,
    var cid: String?,
    var articles: List<CommonArticleBean>?
) {

    data class ChildrenBean(
        var courseId: String?,
        var id: String?,
        var name: String?,
        var order: Int,
        var parentChapterId: Int,
        var userControlSetTop: Boolean,
        var visible: Int,
        var children: List<Any>?
    )
}