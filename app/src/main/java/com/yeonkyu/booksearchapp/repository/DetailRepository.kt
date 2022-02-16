package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.booksearchapp.data.model.BookInfo

interface DetailRepository {
    fun getBookInfo(
        isbn: String,
        onSuccess: (BookInfo) -> Unit,
        onFail: (Exception) -> Unit
    )
}