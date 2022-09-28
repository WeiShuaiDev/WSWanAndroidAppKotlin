package com.linwei.cams.service.home.model


data class SearchBean(val title: String?, val data: List<SearchDetailsBean>?) {

    data class SearchDetailsBean(
        val id: Int,
        val link: String?,
        val name: String?,
        val order: Int,
        val visible: Int
    )
}