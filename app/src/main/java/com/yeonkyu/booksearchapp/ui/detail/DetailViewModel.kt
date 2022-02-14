package com.yeonkyu.booksearchapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.booksearchapp.util.SingleLiveEvent
import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookInfoHandler
import com.yeonkyu.itbooksdk.response.BookInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

) : ViewModel() {

    val title = MutableLiveData("")
    val subTitle = MutableLiveData("")
    val imageUrl = MutableLiveData("")
    val isbn13 = MutableLiveData("")
    val author = MutableLiveData("")
    val description = MutableLiveData("")
    val year = MutableLiveData("")

    val dialogEvent = SingleLiveEvent<String>()

    fun getBookInfo(isbn: String) {
        ItBookStore.fetchBookInfo(
            isbn = isbn,
            itBookInfoHandler = object : ItBookInfoHandler {
                override fun onSuccess(response: BookInfoResponse) {
                    title.postValue(response.title)
                    subTitle.postValue(response.subtitle)
                    imageUrl.postValue(response.img)
                    isbn13.postValue(isbn)
                    author.postValue((response.authors))
                    description.postValue(response.desc)
                    year.postValue(response.year)
                }

                override fun onFail(exception: Exception) {
                    dialogEvent.postValue(exception.message)
                }
            }
        )
    }
}