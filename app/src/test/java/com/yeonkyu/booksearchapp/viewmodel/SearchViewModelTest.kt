package com.yeonkyu.booksearchapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.booksearchapp.data.mapper.toBook
import com.yeonkyu.booksearchapp.repository.SearchRepository
import com.yeonkyu.booksearchapp.ui.search.SearchViewModel
import com.yeonkyu.booksearchapp.util.MockUtil.mock
import com.yeonkyu.booksearchapp.util.MockUtil.mockSearchListResponse_Java
import com.yeonkyu.booksearchapp.util.MockUtil.mockSearchResponse_Java
import com.yeonkyu.itbooksdk.response.SearchListResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private val repository: SearchRepository = mock()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = SearchViewModel(repository)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `fetch Next BookList with Page 1 Test`() {
        // given
        val mockData = mockSearchListResponse_Java()
        Mockito.`when`(repository.searchByKeyword(any(), any(), any(), any(), any())).thenAnswer {
            val onSuccess = it.arguments[3] as ((searchListResponse: SearchListResponse) -> Unit)
            onSuccess.invoke(mockData)
        }

        // when
        viewModel.fetchNextBookList("java", 1)

        // then
        val mockBookList = listOf(mockSearchResponse_Java().toBook())
        assertEquals(mockBookList, viewModel.bookList.value)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `fetch Next BookList with Page 2,3,4,, Test`() {
        // given
        val mockData = mockSearchListResponse_Java()
        Mockito.`when`(repository.searchByKeyword(any(), any(), any(), any(), any())).thenAnswer {
            val onSuccess = it.arguments[3] as ((searchListResponse: SearchListResponse) -> Unit)
            onSuccess.invoke(mockData)
        }

        // when
        viewModel.fetchNextBookList("java" , 1)
        viewModel.fetchNextBookList(null, 2)

        // then
        val mockBookList = listOf(
            mockSearchResponse_Java().toBook(),
            mockSearchResponse_Java().toBook()
        )

        assertEquals(mockBookList, viewModel.bookList.value)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `fetch Next BookList Fail Test`() {
        // given
        val mockException = Exception("Unknown error occur")
        Mockito.`when`(repository.searchByKeyword(any(), any(), any(), any(), any())).thenAnswer {
            val onFail = it.arguments[4] as ((exception: Exception) -> Unit)
            onFail.invoke(mockException)
        }

        // when
        viewModel.fetchNextBookList("java", 1)

        // then
        assertEquals(mockException.message, viewModel.dialogEvent.value)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `reset BookList Test`() {
        // given
        val mockData = mockSearchListResponse_Java()
        Mockito.`when`(repository.searchByKeyword(any(), any(), any(), any(), any())).thenAnswer {
            val onSuccess = it.arguments[3] as ((searchListResponse: SearchListResponse) -> Unit)
            onSuccess.invoke(mockData)
        }

        // when
        viewModel.fetchNextBookList("java", 1)
        assertEquals(false, viewModel.bookList.value?.isEmpty())
        viewModel.resetBookList()

        // then
        assertEquals(true, viewModel.bookList.value?.isEmpty())
    }
}