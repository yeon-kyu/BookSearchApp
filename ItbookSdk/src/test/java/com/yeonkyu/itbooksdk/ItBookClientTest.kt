package com.yeonkyu.itbooksdk

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.yeonkyu.itbooksdk.util.MockUtil.mockSearchListResponse
import com.yeonkyu.itbooksdk.api.ItBookClient
import com.yeonkyu.itbooksdk.api.ItBookHandler
import com.yeonkyu.itbooksdk.api.ItBookService
import com.yeonkyu.itbooksdk.response.SearchListResponse
import com.yeonkyu.itbooksdk.util.SchedulersTestRule
import io.reactivex.rxjava3.core.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify

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
    fun `searchByKeyword Success Test`() {
        // given
        val mockData = mockSearchListResponse()
        Mockito.`when`(service.searchByKeyword("MongoDB", 1)).thenReturn(Single.just(mockData))

        var successData: SearchListResponse? = null
        var failData: Exception? = null

        // when
        client.searchByKeyword("MongoDB", 1, object : ItBookHandler {
            override fun onSuccess(response: SearchListResponse) {
                successData = response
            }

            override fun onFail(exception: Exception) {
                failData = exception
            }
        })

        // then
        verify(service, atLeastOnce()).searchByKeyword("MongoDB", 1)
        assertEquals(false, successData == null)
        assertEquals(true, failData == null)
        assertEquals(mockData, successData)
    }

    @Test
    fun `searchByKeyword Fail Test`() {

    }
}