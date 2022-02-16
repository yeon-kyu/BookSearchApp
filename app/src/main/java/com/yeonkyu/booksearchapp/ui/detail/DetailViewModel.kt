package com.yeonkyu.booksearchapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yeonkyu.booksearchapp.repository.DetailRepository
import com.yeonkyu.booksearchapp.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
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
        repository.getBookInfo(
            isbn = isbn,
            onSuccess = {
                title.postValue(it.title)
                subTitle.postValue(it.subtitle)
                imageUrl.postValue(it.img)
                isbn13.postValue(isbn)
                author.postValue((it.authors))
                description.postValue(it.desc)
                year.postValue(it.year)
            },
            onFail = {
                dialogEvent.postValue(it.message)
            }
        )
    }
}