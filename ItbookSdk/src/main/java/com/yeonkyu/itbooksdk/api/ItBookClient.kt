package com.yeonkyu.itbooksdk.api

import com.yeonkyu.itbooksdk.exception.ErrorType
import com.yeonkyu.itbooksdk.exception.ItBookException
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ItBookClient constructor(
    private val service: ItBookService
) {
    fun searchByKeyword(keyword: String, page: Int, itBookHandler: ItBookHandler) {
        service.searchByKeyword(keyword, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itBookHandler.onSuccess(it)
            }, {
                itBookHandler.onFail(ItBookException(ErrorType.NETWORK_ERROR, it.message))
            })
    }
}