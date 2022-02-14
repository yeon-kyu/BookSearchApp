package com.yeonkyu.itbooksdk.exception

class ItBookException(msg: String, code: Int?) : Exception() {

    override val message: String = msg
    val statusCode = code
}