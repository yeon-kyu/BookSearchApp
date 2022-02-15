package com.yeonkyu.itbooksdk.util

import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.response.SearchResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.mockito.Mockito

object MockUtil {

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

    fun mockSearchResponse_MongoDB() = SearchResponse(
        title = "Practical MongoDB",
        subtitle = "Architecting, Developing, and Administering MongoDB",
        id = "9781484206485",
        image = "https://itbook.store/img/books/9781484206485.png",
        url = "https://itbook.store/books/9781484206485"
    )

    fun mockSearchResponse_Java() = SearchResponse(
        title = "Effective JavaScript",
        subtitle = "68 Specific Ways to Harness the Power of JavaScript",
        id = "9780321812186",
        image = "https://itbook.store/img/books/9780321812186.png",
        url = "https://itbook.store/books/9780321812186"
    )

    fun mockSearchResponse_Java_WithoutScript() = SearchResponse(
        title = "Java Cookbook, 2nd Edition",
        subtitle = "Solutions and Examples for Java Developers",
        id = "9780596007010",
        image = "https://itbook.store/img/books/9780596007010.png",
        url = "https://itbook.store/books/9780596007010"
    )

    fun mockSearchListResponse_MongoDB() = SearchListResponse(
        total = 71,
        page = 1,
        bookList = listOf(
            mockSearchResponse_MongoDB()
        )
    )

    fun mockSearchListResponse_Java() = SearchListResponse(
        total = 1232,
        page = 1,
        bookList = listOf(
            mockSearchResponse_Java(),
            mockSearchResponse_Java_WithoutScript()
        )
    )

    fun mockErrorResponseBody(): ResponseBody {
        val errorResponse = "{\n" + "\"type\": \"error\",\n" + "\"message\": \"What you were looking for isn't here.\"\n" + "}"
        return errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
    }
}