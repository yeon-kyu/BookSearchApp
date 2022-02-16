package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.booksearchapp.data.mapper.toBookInfo
import com.yeonkyu.booksearchapp.data.model.BookInfo
import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookInfoHandler
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.BookInfoResponse
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val itBookStore: ItBookStore
) : DetailRepository {

    override fun getBookInfo(
        isbn: String,
        onSuccess: (BookInfo) -> Unit,
        onFail: (Exception) -> Unit
    ) {
        itBookStore.fetchBookInfo(
            isbn = isbn,
            itBookInfoHandler = object : ItBookInfoHandler {
                override fun onSuccess(response: BookInfoResponse) {
                    onSuccess(response.toBookInfo())
                }

                override fun onFail(exception: ItBookException) {
                    onFail(exception)
                }
            })
    }
}