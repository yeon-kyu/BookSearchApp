package com.yeonkyu.itbooksdk.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName(value = "title")
    val title: String,
    @SerializedName(value = "subtitle")
    val subtitle: String,
    @SerializedName(value = "isbn13")
    val id: String,
    @SerializedName(value = "image")
    val image: String,
    @SerializedName(value = "url")
    val url: String
)
