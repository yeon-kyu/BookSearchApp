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

    fun searchByKeyword(keyword: String, page: Int, itBookHandler: ItBookHandler) {
        getItBookClient().searchByKeyword(keyword, page, itBookHandler)
    }
}