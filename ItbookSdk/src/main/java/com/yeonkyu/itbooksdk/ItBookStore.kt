package com.yeonkyu.itbooksdk

import com.yeonkyu.itbooksdk.api.ItBookClient
import com.yeonkyu.itbooksdk.api.ItBookInfoHandler
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import com.yeonkyu.itbooksdk.exception.ExceptionGenerator

object ItBookStore {

    private var itBookClient: ItBookClient? = null

    private fun getItBookClient(): ItBookClient {
        return itBookClient ?: throw RuntimeException(ExceptionGenerator.IT_BOOK_STORE_NOT_INITIATED)
    }

    @Synchronized
    fun initItBook(): Boolean {
        if (itBookClient == null) {
            val itBookService = NetworkModule.provideItBookService()
            itBookClient = NetworkModule.provideItBookClient(itBookService)
            return true
        } else {
            return false
        }
    }

    fun searchNormal(keyword: String, page: Int, itBookSearchHandler: ItBookSearchHandler) {
        getItBookClient().searchNormal(keyword, page, itBookSearchHandler)
    }

    fun searchWithOperatorAnd(inc1: String, inc2: String, page: Int, itBookSearchHandler: ItBookSearchHandler) {
        getItBookClient().searchWithOperatorAnd(inc1, inc2, page, itBookSearchHandler)
    }

    fun searchWithOperatorNot(inc: String, exc: String, page: Int, itBookSearchHandler: ItBookSearchHandler) {
        getItBookClient().searchWithOperatorNot(inc, exc, page, itBookSearchHandler)
    }

    fun fetchBookInfo(isbn: String, itBookInfoHandler: ItBookInfoHandler) {
        getItBookClient().fetchBookInfo(isbn, itBookInfoHandler)
    }
}