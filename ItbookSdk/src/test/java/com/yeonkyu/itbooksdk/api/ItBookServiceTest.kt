package com.yeonkyu.itbooksdk.api

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
    fun `searchBooks Test`() {
        // given
        enqueueResponse("/SearchListResponse.json")

        // when
        val response = service.searchBooks("MongoDB", 1)
            .blockingGet()
        mockWebServer.takeRequest()

        // then
        assertEquals(71, response.total)
        assertEquals(1, response.page)
        assertEquals("Practical MongoDB", response.bookList[0].title)
        assertEquals("Architecting, Developing, and Administering MongoDB", response.bookList[0].subtitle)
        assertEquals("9781484206485", response.bookList[0].id)
        assertEquals("https://itbook.store/img/books/9781484206485.png", response.bookList[0].image)
        assertEquals("https://itbook.store/books/9781484206485", response.bookList[0].url)
    }

    @Test
    fun `fetchBookInfo Test`() {
        // given
        enqueueResponse("/BookInfoResponse.json")
        val isbn = "9781484206485"

        // when
        val response = service.fetchBookInfo(isbn)
            .blockingGet()
        mockWebServer.takeRequest()

        // then
        assertEquals("0", response.error)
        assertEquals("Practical MongoDB", response.title)
        assertEquals("Architecting, Developing, and Administering MongoDB", response.subtitle)
        assertEquals("Shakuntala Gupta Edward, Navin Sabharwal", response.authors)
        assertEquals("Apress", response.publisher)
        assertEquals("2015", response.year)
        assertEquals("Practical Guide to MongoDB: Architecting, Developing, and Administering MongoDB begins with a short introduction to the basics of NoSQL databases and then introduces readers to MongoDB - the leading document based NoSQL database, acquainting them step-by-step with all aspects of MongoDB.Practical Gu...", response.desc)
        assertEquals("https://itbook.store/img/books/9781484206485.png", response.img)
    }
}