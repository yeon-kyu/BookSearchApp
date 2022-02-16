package com.yeonkyu.booksearchapp.data.mapper

import com.yeonkyu.booksearchapp.data.model.Book
import com.yeonkyu.booksearchapp.data.model.BookInfo
import com.yeonkyu.itbooksdk.response.BookInfoResponse
import com.yeonkyu.itbooksdk.response.SearchResponse

fun SearchResponse.toBook(): Book {
    return Book(
        title = this.title,
        subtitle = this.subtitle,
        id = this.id,
        image = this.image,
        url = this.url
    )
}

fun BookInfoResponse.toBookInfo(): BookInfo {
    return BookInfo(
        title = this.title,
        subtitle = this.subtitle,
        authors = this.authors,
        publisher = this.publisher,
        year = this.year,
        desc = this.desc,
        img = this.img
    )
}