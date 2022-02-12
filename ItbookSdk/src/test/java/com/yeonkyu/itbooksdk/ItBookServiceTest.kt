package com.yeonkyu.itbooksdk

import com.yeonkyu.itbooksdk.api.ItBookService
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ItBookServiceTest : ApiAbstract<ItBookService>() {

    private lateinit var service: ItBookService

    @Before
    fun setup() {
        service = createItBookService(ItBookService::class.java)
    }

    @Test
    fun `search By Keyword Test`() {
        enqueueResponse("/SearchListResponse.json")
        val response = service.searchByKeyword("MongoDB", 1)
            .blockingGet()
        mockWebServer.takeRequest()

        assertEquals(71, response.total)
        assertEquals(1, response.page)
        assertEquals("Practical MongoDB", response.bookList[0].title)
        assertEquals("Architecting, Developing, and Administering MongoDB", response.bookList[0].subtitle)
        assertEquals("9781484206485", response.bookList[0].id)
        assertEquals("https://itbook.store/img/books/9781484206485.png", response.bookList[0].image)
        assertEquals("https://itbook.store/books/9781484206485", response.bookList[0].url)
    }
}