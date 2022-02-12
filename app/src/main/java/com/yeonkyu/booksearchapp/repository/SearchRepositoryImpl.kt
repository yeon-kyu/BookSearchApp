package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookHandler
import com.yeonkyu.itbooksdk.response.SearchListResponse
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(

) : SearchRepository {

    override fun searchByKeyword(
        keyword: String,
        page: Int,
        onStart: () -> Unit,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit
    ) {
        onStart()

        ItBookStore.searchByKeyword(keyword, page, object : ItBookHandler {
            override fun onSuccess(response: SearchListResponse) {
                onSuccess(response)
            }

            override fun onFail(exception: Exception) {
                onFail(exception)
            }

        })
    }
}