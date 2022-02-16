package com.yeonkyu.booksearchapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.booksearchapp.data.mapper.toBook
import com.yeonkyu.booksearchapp.data.model.Book
import com.yeonkyu.booksearchapp.repository.SearchRepository
import com.yeonkyu.booksearchapp.util.SingleLiveEvent
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

    val dialogEvent = SingleLiveEvent<String>()

    private var lastKeyword: String? = null

    /** 2번째 페이지 이후부터는 keyword 가 null */
    fun fetchNextBookList(keyword: String?, page: Int) {
        keyword?.let {
            /** initial search */
            searchByKeyword(keyword = keyword, page = 1)
        } ?: run {
            /** scroll search next */
            searchByKeyword(lastKeyword!!, page)
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
                Timber.e("search error : $it")
                isLoading.postValue(false)
                dialogEvent.postValue(it.message)
            }
        )
    }
}