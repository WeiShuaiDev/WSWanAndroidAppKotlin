package com.linwei.cams.service.base.model

/**
 * 分页实体类
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