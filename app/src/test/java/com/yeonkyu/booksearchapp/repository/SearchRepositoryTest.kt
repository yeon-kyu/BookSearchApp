package com.yeonkyu.booksearchapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.booksearchapp.util.MockUtil.mock
import com.yeonkyu.booksearchapp.util.MockUtil.mockSearchListResponse_Java
import com.yeonkyu.booksearchapp.util.SchedulersTestRule
import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.SearchListResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

class SearchRepositoryTest {

    private lateinit var repository: SearchRepository
    private lateinit var itBookStore: ItBookStore

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulersRule = SchedulersTestRule()

    @Before
    fun setup() {
        itBookStore = mock()
        repository = SearchRepositoryImpl(itBookStore)
    }


    @Test
    fun `searchByKeyword Test`() {
        // given
        val mockHandler: ItBookSearchHandler = object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                print("success")

            }
            override fun onFail(exception: ItBookException) {
                print("fail")

            }
        }

        val mockData = mockSearchListResponse_Java()

        //itBookStore.searchNormal("java", 1, mockHandler)

        //val mockHandler2: ItBookSearchHandler = any2(ItBookSearchHandler::class.java)

        `when`(itBookStore.searchNormal("java", 1, mockHandler))
            .thenAnswer {
                print("!")
                (it.arguments[2] as ItBookSearchHandler).onSuccess(response = mockData)
            }

        var successData: SearchListResponse? = null
        var failData: Exception? = null

        repository.searchByKeyword(
            "java",
            1,
            onStart = { Unit },
            onSuccess = {
                Unit
                        },
            onFail = {
                Unit
            }
        )
        print("successData : $successData")

        // then
        //verify(itBookStore, times(1)).searchNormal("java", 1, mockHandler)

    }
}