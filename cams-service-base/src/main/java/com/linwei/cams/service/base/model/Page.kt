package com.linwei.cams.service.base.model

/**
 * 分页实体类
 * curPage: 当前页数
 * pageCount: 页数
 * offset: 偏移量
 * size: 内容数
 * total: 总内容数
 */
data class Page<T> constructor(
    val curPage: Int,
    val datas: List<T>?,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) {

    constructor() : this(-1, null, -1, false, -1, -1, -1)
}