package com.yeonkyu.booksearchapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.booksearchapp.data.mapper.toBookInfo
import com.yeonkyu.booksearchapp.data.model.BookInfo
import com.yeonkyu.booksearchapp.repository.DetailRepository
import com.yeonkyu.booksearchapp.ui.detail.DetailViewModel
import com.yeonkyu.booksearchapp.util.MockUtil.mock
import com.yeonkyu.booksearchapp.util.MockUtil.mockBookInfoResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val repository: DetailRepository = mock()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `get Book Info Test`() {
        // given
        val mockData = mockBookInfoResponse()
        Mockito.`when`(repository.getBookInfo(any(), any(), any())).thenAnswer {
            val onSuccess = it.arguments[1] as (bookInfo: BookInfo) -> Unit
            onSuccess.invoke(mockData.toBookInfo())
        }

        // when
        val isbn = "123123"
        viewModel.getBookInfo(isbn)

        // then
        assertEquals(mockData.title, viewModel.title.value)
        assertEquals(mockData.subtitle, viewModel.subTitle.value)
        assertEquals(mockData.img, viewModel.imageUrl.value)
        assertEquals(isbn, viewModel.isbn13.value)
        assertEquals(mockData.authors, viewModel.author.value)
        assertEquals(mockData.desc, viewModel.description.value)
        assertEquals(mockData.year, viewModel.year.value)
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun `get Book Info Fail Test`() {
        // given
        val mockException = Exception("Unknown error occur")
        Mockito.`when`(repository.getBookInfo(any(), any(), any())).thenAnswer {
            val onFail = it.arguments[2] as (exception: Exception) -> Unit
            onFail.invoke(mockException)
        }

        // when
        val isbn = "123123"
        viewModel.getBookInfo(isbn)

        // then
        assertEquals(mockException.message, viewModel.dialogEvent.value)
    }
}