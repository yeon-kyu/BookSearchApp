package com.yeonkyu.booksearchapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.booksearchapp.util.MockUtil.mock
import com.yeonkyu.booksearchapp.util.MockUtil.mockSearchListResponse_Java
import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any

class SearchRepositoryTest {

    private lateinit var repository: SearchRepository
    private lateinit var itBookStore: ItBookStore

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        itBookStore = mock()
        repository = SearchRepositoryImpl(itBookStore)
    }

    @Test
    fun `search By Keyword Test - should call searchNormal`() {
        // given
        val mockData = mockSearchListResponse_Java()
        Mockito.`when`(itBookStore.searchNormal(any(), any(), any())).thenAnswer {
            (it.arguments[2] as ItBookSearchHandler).onSuccess(mockData)
        }

        // when + then
        repository.searchByKeyword(
            keyword = "java",
            page = 1,
            onStart = { },
            onSuccess = {
                assertEquals(mockData, it)
            },
            onFail = { }
        )
        verify(itBookStore, times(1)).searchNormal(any(), any(), any())
    }

    @Test
    fun `search By Keyword Test - should call searchWithOperatorAnd`() {
        // given
        val mockData = mockSearchListResponse_Java()
        Mockito.`when`(itBookStore.searchWithOperatorAnd(any(), any(), any(), any())).thenAnswer {
            (it.arguments[3] as ItBookSearchHandler).onSuccess(mockData)
        }

        // when + then
        repository.searchByKeyword(
            keyword = "java|kotlin",
            page = 1,
            onStart = { },
            onSuccess = {
                assertEquals(mockData, it)
            },
            onFail = { }
        )
        verify(itBookStore, times(1)).searchWithOperatorAnd(any(), any(), any(), any())
    }

    @Test
    fun `search By Keyword Test - should call searchWithOperatorNot`() {
        // given
        val mockData = mockSearchListResponse_Java()
        Mockito.`when`(itBookStore.searchWithOperatorNot(any(), any(), any(), any())).thenAnswer {
            (it.arguments[3] as ItBookSearchHandler).onSuccess(mockData)
        }

        // when + then
        repository.searchByKeyword(
            keyword = "java-kotlin",
            page = 1,
            onStart = { },
            onSuccess = {
                assertEquals(mockData, it)
            },
            onFail = { }
        )
        verify(itBookStore, times(1)).searchWithOperatorNot(any(), any(), any(), any())
    }
}