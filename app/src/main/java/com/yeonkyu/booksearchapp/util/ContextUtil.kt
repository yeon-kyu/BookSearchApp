package com.yeonkyu.booksearchapp.util

import android.app.Dialog
import android.content.Context
import com.yeonkyu.booksearchapp.ui.dialogs.BookSearchDialog

fun Context.makeDialog(mainText: String, subText: String?): Dialog {
    val dialog = BookSearchDialog(this).apply {
        setMainText(mainText)
        subText?.let {
            setSubText(subText)
        }
    }
    dialog.show()
    return dialog
}