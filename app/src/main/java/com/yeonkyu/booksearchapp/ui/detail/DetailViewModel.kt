package com.yeonkyu.booksearchapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class DetailViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val subTitle = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val isbn = MutableLiveData<String>()
}