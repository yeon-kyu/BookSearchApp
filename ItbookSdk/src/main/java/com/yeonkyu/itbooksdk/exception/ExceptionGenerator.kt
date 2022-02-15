package com.yeonkyu.itbooksdk.exception

internal object ExceptionGenerator {
    fun generateHttpException(statusCode: Int): ItBookException {
        return when (statusCode) {
            in 100..199 -> { ItBookException(INFORMATION_RESPONSE, statusCode) }
            in 300..399 -> { ItBookException(REDIRECTION_MESSAGE, statusCode) }
            in 400..499 -> { ItBookException(SDK_ERROR, statusCode) }
            in 500..599 -> { ItBookException(IT_BOOK_API_ERROR, statusCode) }
            else -> { ItBookException(UNKNOWN_ERROR, statusCode) }
        }
    }

    const val IT_BOOK_STORE_NOT_INITIATED = "you must first call initItBook()"

    const val INFORMATION_RESPONSE = "INFORMATION_RESPOND"
    const val SDK_ERROR = "SDK_ERROR"
    const val REDIRECTION_MESSAGE = "REDIRECTION_MESSAGE"
    const val IT_BOOK_API_ERROR = "IT_BOOK_API_ERROR"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
}