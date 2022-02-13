package com.yeonkyu.booksearchapp.data.mapper

import com.yeonkyu.booksearchapp.data.model.Book
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