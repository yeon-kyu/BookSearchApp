package com.yeonkyu.booksearchapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.booksearchapp.data.mapper.toBookInfo
import com.yeonkyu.booksearchapp.util.MockUtil.mockBookInfoResponse
import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookInfoHandler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DetailRepositoryTest {

    private lateinit var repository: DetailRepository
    private val itBookStore: ItBookStore = mock()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = DetailRepositoryImpl(itBookStore)
    }

    @Test
    fun `get Book Info Test`() {
        // given
        val mockData = mockBookInfoResponse()
        Mockito.`when`(itBookStore.fetchBookInfo(any(), any())).thenAnswer {
            (it.arguments[1] as ItBookInfoHandler).onSuccess(mockData)
        }

        // when + then
        repository.getBookInfo(
            isbn = "123123",
            onSuccess = {
                assertEquals(mockData.toBookInfo(), it)
            },
            onFail = { }
        )
        verify(itBookStore, times(1)).fetchBookInfo(any(), any())
    }
}