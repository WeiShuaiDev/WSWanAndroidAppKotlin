package com.linwei.cams.service.publis.model

data class PublisAuthorBean(
    val courseId: String?,
    val id: String?,
    val name: String?,
    val order: Int,
    val parentChapterId: String,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: List<Any>
)