package com.yeonkyu.itbooksdk

import com.yeonkyu.itbooksdk.api.ItBookClient
import com.yeonkyu.itbooksdk.api.ItBookHandler

object ItBookStore {

    private var itBookClient: ItBookClient? = null

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

    private fun getItBookClient(): ItBookClient {
        return itBookClient ?: throw Exception("you must first call initItBook()")
    }

    fun searchNormal(keyword: String, page: Int, itBookHandler: ItBookHandler) {
        getItBookClient().searchNormal(keyword, page, itBookHandler)
    }

    fun searchWithOperatorAnd(inc1: String, inc2: String, page: Int, itBookHandler: ItBookHandler) {
        getItBookClient().searchWithOperatorAnd(inc1, inc2, page, itBookHandler)
    }

    fun searchWithOperatorNot(inc: String, exc: String, page: Int, itBookHandler: ItBookHandler) {
        getItBookClient().searchWithOperatorNot(inc, exc, page, itBookHandler)
    }
}