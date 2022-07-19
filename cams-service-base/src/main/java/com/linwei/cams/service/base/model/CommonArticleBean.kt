package com.linwei.cams.service.base.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.linwei.cams.service.base.constants.ItemTypeConstants

data class CommonArticleBean(
    val apkLink: String?,
    val audit: Int,
    val author: String?,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String?,
    var collect: Boolean,
    val courseId: Int,
    val desc: String?,
    val descMd: String?,
    val envelopePic: String?,
    val fresh: Boolean,
    val host: String?,
    val id: Int,
    val link: String?,
    val niceDate: String?,
    val niceShareDate: String?,
    val origin: String?,
    val prefix: String?,
    val projectLink: String?,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String?,
    val superChapterId: Int,
    val superChapterName: String?,
    val tags: List<Tag>,
    val title: String?,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) : MultiItemEntity {
    override val itemType: Int
        get() = if (envelopePic.isNullOrEmpty()) ItemTypeConstants.ARTICLE_ITEM_TEXT
        else ItemTypeConstants.ARTICLE_ITEM_TEXT_PIC
}

data class Tag(
    val name: String,
    val url: String
)