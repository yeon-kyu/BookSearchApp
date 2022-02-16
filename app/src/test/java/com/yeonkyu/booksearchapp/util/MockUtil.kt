package com.yeonkyu.booksearchapp.util

import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.response.SearchResponse
import org.mockito.Mockito

object MockUtil {

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    fun mockSearchResponse_Java() = SearchResponse(
        title = "Effective JavaScript",
        subtitle = "68 Specific Ways to Harness the Power of JavaScript",
        id = "9780321812186",
        image = "https://itbook.store/img/books/9780321812186.png",
        url = "https://itbook.store/books/9780321812186"
    )

    fun mockSearchListResponse_Java() = SearchListResponse(
        total = 1232,
        page = 1,
        bookList = listOf(
            mockSearchResponse_Java()
        )
    )
}