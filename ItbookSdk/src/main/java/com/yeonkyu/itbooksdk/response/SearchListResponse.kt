package com.yeonkyu.itbooksdk.response

import com.google.gson.annotations.SerializedName

data class SearchListResponse(
    @SerializedName(value = "total")
    val total: Int,
    @SerializedName(value = "page")
    val page: Int,
    @SerializedName(value = "books")
    val bookList: List<SearchResponse>
)