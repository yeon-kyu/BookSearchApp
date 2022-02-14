package com.yeonkyu.itbooksdk.response

import com.google.gson.annotations.SerializedName

data class BookInfoResponse (
    @SerializedName(value = "error")
    val error: String,
    @SerializedName(value = "title")
    val title: String,
    @SerializedName(value = "subtitle")
    val subtitle: String,
    @SerializedName(value = "authors")
    val authors: String,
    @SerializedName(value = "publisher")
    val publisher: String,
    @SerializedName(value = "year")
    val year: String,
    @SerializedName(value = "desc")
    val desc: String,
    @SerializedName(value = "image")
    val img: String
)