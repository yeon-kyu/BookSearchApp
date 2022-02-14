package com.yeonkyu.itbooksdk

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchListResponse
import com.yeonkyu.itbooksdk.api.ItBookClient
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import com.yeonkyu.itbooksdk.api.ItBookService
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.exception.ExceptionGenerator
import com.yeonkyu.itbooksdk.util.MockUtil.mockErrorResponseBody
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

    @Test
    fun `searchBooks Success Test`() {
        // given
        val mockData = mockSearchListResponse()
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
    fun `searchBooks Http Exception Test`() {
        // given
        val mockErrorResponseBody = mockErrorResponseBody()
        val mockResponse = Response.error<SearchListResponse>(404, mockErrorResponseBody)

        Mockito.`when`(service.searchBooks("MongoDB", 1))
            .thenReturn(Single.error(HttpException(mockResponse)))

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
    fun `searchBooks Other Exception Test`() {
        // given
        val mockMessage = "foo"
        val mockException = RuntimeException(mockMessage)

        Mockito.`when`(service.searchBooks("MongoDB", 1))
            .thenReturn(Single.error(mockException))

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
}