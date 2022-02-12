package com.yeonkyu.booksearchapp.initializer

import android.content.Context
import androidx.startup.Initializer
import com.yeonkyu.itbooksdk.ItBookStore

class ItBookInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        ItBookStore.initItBook()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}