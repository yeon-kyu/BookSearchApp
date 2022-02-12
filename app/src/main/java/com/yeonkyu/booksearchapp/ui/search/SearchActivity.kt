package com.yeonkyu.booksearchapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeonkyu.booksearchapp.R
import com.yeonkyu.itbooksdk.ItBookStore
import com.yeonkyu.itbooksdk.api.ItBookHandler
import com.yeonkyu.itbooksdk.response.SearchListResponse
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ItBookStore.searchByKeyword("MongoDB", 1, object : ItBookHandler {
            override fun onSuccess(response: SearchListResponse) {
                Timber.e("response : ${response}")
            }

            override fun onFail(exception: Exception) {
                Timber.e("exception : $exception")
            }
        })
    }
}