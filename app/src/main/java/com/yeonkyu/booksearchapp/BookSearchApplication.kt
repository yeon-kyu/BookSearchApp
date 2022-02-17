package com.yeonkyu.booksearchapp

import android.app.Application
import com.yeonkyu.itbooksdk.ItBookStore
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BookSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ItBookStore.initItBook()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}