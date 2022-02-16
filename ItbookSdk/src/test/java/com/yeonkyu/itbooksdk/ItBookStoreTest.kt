package com.yeonkyu.itbooksdk

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.itbooksdk.api.ItBookInfoHandler
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import com.yeonkyu.itbooksdk.exception.ExceptionGenerator
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.BookInfoResponse
import com.yeonkyu.itbooksdk.response.SearchListResponse
import org.junit.Assert.*
import org.junit.Test

internal class ItBookStoreTest {

    @Test
    fun `searchNormal Exception Test`() {
        // when + then
        val thrown = assertThrows(RuntimeException::class.java) {
            ItBookStore.searchNormal("mongoDB", 1, object : ItBookSearchHandler {
                override fun onSuccess(response: SearchListResponse) = Unit
                override fun onFail(exception: ItBookException) = Unit
            })
        }
        assertEquals(ExceptionGenerator.IT_BOOK_STORE_NOT_INITIATED, thrown.message)
    }

    @Test
    fun `searchWithOperatorAnd Exception Test`() {
        // when + then
        val thrown = assertThrows(RuntimeException::class.java) {
            ItBookStore.searchWithOperatorAnd("mongoDB", "java", 1, object : ItBookSearchHandler {
                override fun onSuccess(response: SearchListResponse) = Unit
                override fun onFail(exception: ItBookException) = Unit
            })
        }
        assertEquals(ExceptionGenerator.IT_BOOK_STORE_NOT_INITIATED, thrown.message)
    }

    @Test
    fun `searchWithOperatorNot Exception Test`() {
        // when + then
        val thrown = assertThrows(RuntimeException::class.java) {
            ItBookStore.searchWithOperatorNot("mongoDB", "java", 1, object : ItBookSearchHandler {
                override fun onSuccess(response: SearchListResponse) = Unit
                override fun onFail(exception: ItBookException) = Unit
            })
        }
        assertEquals(ExceptionGenerator.IT_BOOK_STORE_NOT_INITIATED, thrown.message)
    }

    @Test
    fun `fetchBookInfo Exception Test`() {
        // when + then
        val thrown = assertThrows(RuntimeException::class.java) {
            ItBookStore.fetchBookInfo("9781484206485", object : ItBookInfoHandler {
                override fun onSuccess(response: BookInfoResponse) = Unit
                override fun onFail(exception: ItBookException) = Unit
            })
        }
        assertEquals(ExceptionGenerator.IT_BOOK_STORE_NOT_INITIATED, thrown.message)
    }
}