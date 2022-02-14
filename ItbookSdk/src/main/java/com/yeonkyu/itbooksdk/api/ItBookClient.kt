package com.yeonkyu.itbooksdk.api

import com.yeonkyu.itbooksdk.exception.ErrorType
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.SearchListResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

internal class ItBookClient constructor(
    private val service: ItBookService
) {
    fun searchNormal(keyword: String, page: Int, itBookSearchHandler: ItBookSearchHandler) {
        service.searchBooks(keyword, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itBookSearchHandler.onSuccess(it)
            }, {
                itBookSearchHandler.onFail(ItBookException(ErrorType.NETWORK_ERROR, it.message))
            })
    }

    fun searchWithOperatorAnd(inc1: String, inc2: String, page: Int, itBookSearchHandler: ItBookSearchHandler) {
        Single.zip(
            service.searchBooks(inc1, page),
            service.searchBooks(inc2, page),
            { leftResponse, rightResponse ->
                SearchListResponse(
                    total = leftResponse.total + rightResponse.total,
                    page = leftResponse.page,
                    bookList = leftResponse.bookList.union(rightResponse.bookList).toList()
                )
            }
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itBookSearchHandler.onSuccess(it)
            }, {
                itBookSearchHandler.onFail(ItBookException(ErrorType.NETWORK_ERROR, it.message))
            })
    }

    fun searchWithOperatorNot(inc: String, exc: String, page: Int, itBookSearchHandler: ItBookSearchHandler) {
        service.searchBooks(inc, page)
            .subscribeOn(Schedulers.io())
            .map {
                val incLowercase = inc.lowercase()
                val excLowercase = exc.lowercase()
                SearchListResponse(
                    total = it.total,
                    page = it.page,
                    bookList = it.bookList.filter { book ->
                        val bookTitle = book.title.lowercase()
                        bookTitle.contains(incLowercase) && !bookTitle.contains(excLowercase)
                    }
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itBookSearchHandler.onSuccess(it)
            }, {
                itBookSearchHandler.onFail(ItBookException(ErrorType.NETWORK_ERROR, it.message))
            })
    }

    fun fetchBookInfo(isbn: String, itBookInfoHandler: ItBookInfoHandler) {
        service.fetchBookInfo(isbn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itBookInfoHandler.onSuccess(it)
            }, {
                itBookInfoHandler.onFail(ItBookException(ErrorType.NETWORK_ERROR, it.message))
            })
    }
}