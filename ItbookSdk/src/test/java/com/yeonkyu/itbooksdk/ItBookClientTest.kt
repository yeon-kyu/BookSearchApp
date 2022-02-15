package com.yeonkyu.itbooksdk

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.itbooksdk.api.ItBookClient
import com.yeonkyu.itbooksdk.api.ItBookInfoHandler
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import com.yeonkyu.itbooksdk.api.ItBookService
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.exception.ExceptionGenerator
import com.yeonkyu.itbooksdk.response.BookInfoResponse
import com.yeonkyu.itbooksdk.util.MockUtil.mockBookInfoResponse
import com.yeonkyu.itbooksdk.util.MockUtil.mockErrorResponseBody
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchListResponse_Java
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchListResponse_MongoDB
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchResponse_Java
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchResponse_Java_WithoutScript
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchResponse_MongoDB
import com.yeonkyu.itbooksdk.util.SchedulersTestRule
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import retrofit2.HttpException
import retrofit2.Response

class ItBookClientTest {

    private lateinit var client: ItBookClient
    private val service: ItBookService = Mockito.mock(ItBookService::class.java)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val testSchedulersRule = SchedulersTestRule()

    @Before
    fun setup() {
        client = NetworkModule.provideItBookClient(service)
    }

    /** searchNormal() Test */
    @Test
    fun `searchNormal Success Test`() {
        // given
        val mockData = mockSearchListResponse_MongoDB()
        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.just(mockData))

        var successData: SearchListResponse? = null
        var failData: Exception? = null

        // when
        client.searchNormal("MongoDB", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        assertEquals(false, successData == null)
        assertEquals(true, failData == null)
        assertEquals(mockData, successData)
    }

    @Test
    fun `searchNormal Http Exception Test`() {
        // given
        val mockErrorResponseBody = mockErrorResponseBody()
        val mockResponse = Response.error<SearchListResponse>(404, mockErrorResponseBody)

        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.error(HttpException(mockResponse)))

        var successData: SearchListResponse? = null
        var failData: ItBookException? = null

        // when
        client.searchNormal("MongoDB", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(404, failData!!.statusCode)
        assertEquals(ExceptionGenerator.SDK_ERROR, failData!!.message)
    }

    @Test
    fun `searchNormal Other Exception Test`() {
        // given
        val mockMessage = "foo"
        val mockException = RuntimeException(mockMessage)

        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.error(mockException))

        var successData: SearchListResponse? = null
        var failData: ItBookException? = null

        // when
        client.searchNormal("MongoDB", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(null, failData!!.statusCode)
        assertEquals(mockMessage, failData!!.message)
    }


    /** searchWithOperatorAnd() Test */
    @Test
    fun `searchWithOperatorAnd Success Test`() {
        // given
        val mockMongoDbData = mockSearchListResponse_MongoDB()
        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.just(mockMongoDbData))

        val mockJavaData = mockSearchListResponse_Java()
        Mockito.`when`(service.searchBooks("java", 1)).thenReturn(Single.just(mockJavaData))

        var successData: SearchListResponse? = null
        var failData: Exception? = null

        // when
        client.searchWithOperatorAnd("MongoDB", "java", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        verify(service, atLeastOnce()).searchBooks("java", 1)
        assertEquals(false, successData == null)
        assertEquals(true, failData == null)

        val expected = SearchListResponse(
            total = 1303,
            page = 1,
            bookList = listOf(
                mockSearchResponse_MongoDB(),
                mockSearchResponse_Java(),
                mockSearchResponse_Java_WithoutScript()
            )
        )
        assertEquals(expected, successData)
    }

    @Test
    fun `searchWithOperatorAnd Http Exception Test`() {
        // given
        val mockErrorResponseBody = mockErrorResponseBody()
        val mockResponse = Response.error<SearchListResponse>(404, mockErrorResponseBody)
        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.error(HttpException(mockResponse)))

        val mockJavaData = mockSearchListResponse_Java()
        Mockito.`when`(service.searchBooks("java", 1)).thenReturn(Single.just(mockJavaData))

        var successData: SearchListResponse? = null
        var failData: ItBookException? = null

        // when
        client.searchWithOperatorAnd("MongoDB", "java", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        verify(service, atLeastOnce()).searchBooks("java", 1)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(404, failData!!.statusCode)
        assertEquals(ExceptionGenerator.SDK_ERROR, failData!!.message)
    }

    @Test
    fun `searchWithOperatorAnd other Exception Test`() {
        // given
        val mockMessage = "foo"
        val mockException = RuntimeException(mockMessage)
        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.error(mockException))

        val mockJavaData = mockSearchListResponse_Java()
        Mockito.`when`(service.searchBooks("java", 1)).thenReturn(Single.just(mockJavaData))

        var successData: SearchListResponse? = null
        var failData: ItBookException? = null

        // when
        client.searchWithOperatorAnd("MongoDB", "java", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        verify(service, atLeastOnce()).searchBooks("java", 1)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(null, failData!!.statusCode)
        assertEquals(mockMessage, failData!!.message)
    }


    /** searchWithOperatorNot() Test */
    @Test
    fun `searchWithOperatorNot Success Test`() {
        // given
        val mockJavaData = mockSearchListResponse_Java()
        Mockito.`when`(service.searchBooks("java", 1)).thenReturn(Single.just(mockJavaData))

        var successData: SearchListResponse? = null
        var failData: Exception? = null

        // when
        client.searchWithOperatorNot("java", "script", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("java", 1)
        assertEquals(false, successData == null)
        assertEquals(true, failData == null)

        val expected = SearchListResponse(
            total = mockJavaData.total,
            page = mockJavaData.page,
            bookList = listOf(
                mockSearchResponse_Java_WithoutScript()
            )
        )
        assertEquals(expected, successData)
    }

    @Test
    fun `searchWithOperatorNot Http Exception Test`() {
        // given
        val mockErrorResponseBody = mockErrorResponseBody()
        val mockResponse = Response.error<SearchListResponse>(404, mockErrorResponseBody)
        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.error(HttpException(mockResponse)))

        var successData: SearchListResponse? = null
        var failData: ItBookException? = null

        // when
        client.searchWithOperatorNot("MongoDB", "java", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(404, failData!!.statusCode)
        assertEquals(ExceptionGenerator.SDK_ERROR, failData!!.message)
    }

    @Test
    fun `searchWithOperatorNot other Exception Test`() {
        // given
        val mockMessage = "foo"
        val mockException = RuntimeException(mockMessage)
        Mockito.`when`(service.searchBooks("MongoDB", 1)).thenReturn(Single.error(mockException))

        var successData: SearchListResponse? = null
        var failData: ItBookException? = null

        // when
        client.searchWithOperatorNot("MongoDB", "java", 1, object : ItBookSearchHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchBooks("MongoDB", 1)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(null, failData!!.statusCode)
        assertEquals(mockMessage, failData!!.message)
    }


    /** fetchBookInfo() Test */
    @Test
    fun `fetchBookInfo Success Test`() {
        // given
        val isbn = "9781484206485"
        val mockData = mockBookInfoResponse()
        Mockito.`when`(service.fetchBookInfo(isbn)).thenReturn(Single.just(mockData))

        var successData: BookInfoResponse? = null
        var failData: ItBookException? = null

        // when
        client.fetchBookInfo(isbn, itBookInfoHandler = object : ItBookInfoHandler {
            override fun onSuccess(response: BookInfoResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).fetchBookInfo(isbn)
        assertEquals(false, successData == null)
        assertEquals(true, failData == null)
        assertEquals(mockData, successData)
    }

    @Test
    fun `fetchBookInfo Http Exception Test`() {
        // given
        val isbn = "9781484206485"
        val mockErrorResponseBody = mockErrorResponseBody()
        val mockResponse = Response.error<BookInfoResponse>(404, mockErrorResponseBody)
        Mockito.`when`(service.fetchBookInfo(isbn)).thenReturn(Single.error(HttpException(mockResponse)))

        var successData: BookInfoResponse? = null
        var failData: ItBookException? = null

        // when
        client.fetchBookInfo(isbn, itBookInfoHandler = object : ItBookInfoHandler {
            override fun onSuccess(response: BookInfoResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).fetchBookInfo(isbn)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(404, failData!!.statusCode)
        assertEquals(ExceptionGenerator.SDK_ERROR, failData!!.message)
    }

    @Test
    fun `fetchBookInfo other Exception Test`() {
        // given
        val isbn = "9781484206485"
        val mockMessage = "foo"
        val mockException = RuntimeException(mockMessage)
        Mockito.`when`(service.fetchBookInfo(isbn)).thenReturn(Single.error(mockException))

        var successData: BookInfoResponse? = null
        var failData: ItBookException? = null

        // when
        client.fetchBookInfo(isbn, itBookInfoHandler = object : ItBookInfoHandler {
            override fun onSuccess(response: BookInfoResponse) {
                successData = response
            }

            override fun onFail(exception: ItBookException) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).fetchBookInfo(isbn)
        assertEquals(true, successData == null)
        assertEquals(false, failData == null)
        assertEquals(null, failData!!.statusCode)
        assertEquals(mockMessage, failData!!.message)
    }
}