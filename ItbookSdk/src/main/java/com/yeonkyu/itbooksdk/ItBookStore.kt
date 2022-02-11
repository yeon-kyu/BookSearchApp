package com.yeonkyu.itbooksdk

import com.yeonkyu.itbooksdk.api.ItBookClient

object ItBookStore {

    private var itBookClient: ItBookClient? = null

    fun getItBookClient(): ItBookClient {
        return itBookClient ?: throw Exception("you must first call initItBook()")
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
}