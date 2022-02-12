package com.yeonkyu.booksearchapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.booksearchapp.data.model.Book
import com.yeonkyu.booksearchapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {

    private val _bookList = ArrayList<Book>()
    val bookList = MutableLiveData<List<Book>>()

    private var page = 1

    fun searchByKeyword(keyword: String) {
        repository.searchByKeyword(
            keyword = keyword,
            page = page,
            onStart = { },
            onSuccess = {
                for(book in it.bookList) {
                    _bookList.add(Book(
                        book.title,
                        book.subtitle,
                        book.id,
                        book.image,
                        book.url
                    ))
                }
                bookList.postValue(_bookList)
                page++
            },
            onFail = { }
        )
    }
}