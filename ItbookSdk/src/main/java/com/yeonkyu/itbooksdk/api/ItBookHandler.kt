package com.yeonkyu.itbooksdk.api

import com.yeonkyu.itbooksdk.response.SearchListResponse

interface ItBookHandler {
    fun onSuccess(response: SearchListResponse)
    fun onFail(exception: Exception)
}