package com.yeonkyu.booksearchapp.util

import com.yeonkyu.itbooksdk.response.BookInfoResponse
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

    fun mockBookInfoResponse() = BookInfoResponse(
        error = "0",
        title = "Practical MongoDB",
        subtitle = "Architecting, Developing, and Administering MongoDB",
        authors = "Shakuntala Gupta Edward, Navin Sabharwal",
        publisher = "Apress",
        year = "2015",
        desc = "Practical Guide to MongoDB: Architecting, Developing, and Administering MongoDB begins with a short introduction to the basics of NoSQL databases and then introduces readers to MongoDB - the leading document based NoSQL database, acquainting them step-by-step with all aspects of MongoDB.Practical Gu...",
        img = "https://itbook.store/img/books/9781484206485.png"
    )
}