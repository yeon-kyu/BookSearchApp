package com.yeonkyu.itbooksdk.api

import com.yeonkyu.itbooksdk.response.BookInfoResponse
import com.yeonkyu.itbooksdk.response.SearchListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ItBookService {
    @GET("1.0/search/{query}/{page}")
    fun searchBooks(
        @Path("query") keyword: String,
        @Path("page") page: Int
    ): Single<SearchListResponse>

    @GET("1.0/books/{isbn13}")
    fun fetchBookInfo(
        @Path("isbn13") isbn: String
    ): Single<BookInfoResponse>
}