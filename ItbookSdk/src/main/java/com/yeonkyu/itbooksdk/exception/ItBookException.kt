package com.yeonkyu.itbooksdk.exception

class ItBookException(private val errorType: ErrorType, msg: String?) : Exception() {
    override val message: String? = msg
}