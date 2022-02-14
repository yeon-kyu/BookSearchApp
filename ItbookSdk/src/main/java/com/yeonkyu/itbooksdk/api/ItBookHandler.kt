package com.yeonkyu.itbooksdk.api

import com.yeonkyu.itbooksdk.response.BookInfoResponse
import com.yeonkyu.itbooksdk.response.SearchListResponse

interface ItBookSearchHandler {
    fun onSuccess(response: SearchListResponse)
    fun onFail(exception: Exception)
}

interface ItBookInfoHandler {
    fun onSuccess(response: BookInfoResponse)
    fun onFail(exception: Exception)
}