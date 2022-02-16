package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.response.SearchListResponse
import dagger.hilt.android.testing.HiltAndroidTest

@HiltAndroidTest
class FakeSearchRepository constructor(
    private val itBookStore: ItBookStore
) : SearchRepository {

    override fun searchByKeyword(
        keyword: String,
        page: Int,
        onStart: () -> Unit,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit,
    ) {

    }

}