package com.yeonkyu.booksearchapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.booksearchapp.data.mapper.toBook
import com.yeonkyu.booksearchapp.data.model.Book
import com.yeonkyu.booksearchapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
): ViewModel() {

    val isLoading = MutableLiveData(false)
    val isEnd = MutableLiveData(true)

    private val _bookList = ArrayList<Book>()
    val bookList = MutableLiveData<List<Book>>()

    private var lastKeyword: String? = null

    fun fetchNextBookList(keyword: String?, page: Int) {
        if (lastKeyword == null && keyword != null) {
            /** initial search */
            searchByKeyword(keyword = keyword, page = 1)
            return
        }
        lastKeyword?.let {
            searchByKeyword(it, page)
        }
    }

    fun resetBookList() {
        _bookList.clear()
        lastKeyword = null
    }

    private fun searchByKeyword(keyword: String, page: Int) {
        repository.searchByKeyword(
            keyword = keyword,
            page = page,
            onStart = { isLoading.value = true },
            onSuccess = {
                lastKeyword = keyword

                for (bookResponse in it.bookList) {
                    _bookList.add(bookResponse.toBook())
                }
                bookList.postValue(_bookList)
                isLoading.postValue(false)
                isEnd.postValue(it.bookList.isEmpty())
            },
            onFail = {
                isLoading.postValue(false)
                Timber.e("search error $it")
                //todo 다이얼로그 띄우기
            }
        )
    }
}