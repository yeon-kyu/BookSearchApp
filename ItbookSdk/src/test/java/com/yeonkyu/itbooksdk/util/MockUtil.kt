package com.yeonkyu.itbooksdk.util

import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.response.SearchResponse
import org.mockito.Mockito

object MockUtil {

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    fun mockSearchResponse() = SearchResponse(
        title = "Practical MongoDB",
        subtitle = "Architecting, Developing, and Administering MongoDB",
        id = "9781484206485",
        image = "https://itbook.store/img/books/9781484206485.png",
        url = "https://itbook.store/books/9781484206485"
    )

    fun mockSearchListResponse() = SearchListResponse(
        total = 71,
        page = 1,
        bookList = listOf(
            mockSearchResponse()
        )
    )
}