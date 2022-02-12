package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookHandler
import com.yeonkyu.itbooksdk.response.SearchListResponse
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(

) : SearchRepository {

    fun searchByKeyword(
        keyword: String,
        page: Int,
        onStart: () -> Unit,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        onStart()

        ItBookStore.searchByKeyword(keyword, page, object : ItBookHandler {
            override fun onSuccess(response: SearchListResponse) {
                onSuccess()
            }

            override fun onFail(exception: Exception) {
                onFail()
            }

        })
    }
}