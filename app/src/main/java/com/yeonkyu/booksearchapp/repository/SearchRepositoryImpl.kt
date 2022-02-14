package com.yeonkyu.booksearchapp.repository

import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookSearchHandler
import com.yeonkyu.itbooksdk.exception.ItBookException
import com.yeonkyu.itbooksdk.response.SearchListResponse
import timber.log.Timber
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(

) : SearchRepository {

    override fun searchByKeyword(
        keyword: String,
        page: Int,
        onStart: () -> Unit,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit
    ) {
        onStart()

        when {
            hasTwoOrMoreOperation(keyword) -> onFail(Exception("only one operator are available"))
            keyword.contains("|") -> searchWithOperatorAnd(keyword, page, onSuccess, onFail)
            keyword.contains("-") -> searchWithOperatorNot(keyword, page, onSuccess, onFail)
            else -> searchNormal(keyword, page, onSuccess, onFail)
        }
    }

    private fun hasTwoOrMoreOperation(keyword: String): Boolean {
        return (keyword.contains("|") && keyword.contains("-"))
                || ((keyword.split("|").size > 2))
                || (keyword.split("-").size > 2)
    }

    private fun searchWithOperatorAnd(
        keyword: String,
        page: Int,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit
    ) {
        Timber.e("searchWithOperatorAnd start")
        val splitKeyword = keyword.split("|")
        ItBookStore.searchWithOperatorAnd(
            inc1 = splitKeyword[0],
            inc2 = splitKeyword[1],
            page = page,
            itBookSearchHandler = object : ItBookSearchHandler {
                override fun onSuccess(response: SearchListResponse) {
                    onSuccess(response)
                }

                override fun onFail(exception: ItBookException) {
                    onFail(exception)
                }
            }
        )
    }

    private fun searchWithOperatorNot(
        keyword: String,
        page: Int,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit
    ) {
        val splitKeyword = keyword.split("-")
        ItBookStore.searchWithOperatorNot(
            inc = splitKeyword[0],
            exc = splitKeyword[1],
            page = page,
            itBookSearchHandler = object : ItBookSearchHandler {
                override fun onSuccess(response: SearchListResponse) {
                    onSuccess(response)
                }

                override fun onFail(exception: ItBookException) {
                    onFail(exception)
                }
            }
        )
    }

    private fun searchNormal(
        keyword: String,
        page: Int,
        onSuccess: (SearchListResponse) -> Unit,
        onFail: (Exception) -> Unit
    ) {
        ItBookStore.searchNormal(
            keyword = keyword,
            page = page,
            itBookSearchHandler = object : ItBookSearchHandler {
                override fun onSuccess(response: SearchListResponse) {
                    onSuccess(response)
                }

                override fun onFail(exception: ItBookException) {
                    onFail(exception)
                }
            })
    }
}