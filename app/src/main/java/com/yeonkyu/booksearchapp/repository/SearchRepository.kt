package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.itbooksdk.response.SearchListResponse

interface SearchRepository {
    fun searchByKeyword(
        keyword: String,
        page: Int,
        onStart: () -> Unit,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit
    )
}