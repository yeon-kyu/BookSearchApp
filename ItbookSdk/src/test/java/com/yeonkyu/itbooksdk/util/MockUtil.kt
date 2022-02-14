package com.yeonkyu.itbooksdk.util

import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.response.SearchResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
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

    fun mockErrorResponseBody(): ResponseBody {
        val errorResponse = "{\n" + "\"type\": \"error\",\n" + "\"message\": \"What you were looking for isn't here.\"\n" + "}"
        return errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
    }
}