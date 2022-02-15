package com.yeonkyu.booksearchapp.ui.detail

import android.os.Bundle
import android.transition.Transition
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import com.yeonkyu.booksearchapp.R
import com.yeonkyu.booksearchapp.databinding.ActivityDetailBinding
import com.yeonkyu.booksearchapp.util.makeDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpView()
        observeDialogEvent()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setUpView() {
        val isbn = intent.getStringExtra(BOOK_ISBN)
        val imgUrl = intent.getStringExtra(BOOK_IMAGE_URL)

        if (isbn != null) {
            viewModel.getBookInfo(isbn)
        } else {
            makeDialog(getString(R.string.detail_no_isbn_alert), null)
        }
        imgUrl?.let {
            viewModel.imageUrl.postValue(imgUrl)
        }

        ViewCompat.setTransitionName(binding.detailImg, VIEW_NAME_IMAGE)
        addTransitionListener()

        binding.detailCloseBt.setOnClickListener {
            finish()
        }
    }

    private fun observeDialogEvent() {
        viewModel.dialogEvent.observe(this) {
            makeDialog(it, null)
        }
    }

    private fun addTransitionListener() {
        window.sharedElementEnterTransition?.apply {
            addListener(object : Transition.TransitionListener {
                override fun onTransitionStart(transition: Transition?) = Unit

                override fun onTransitionEnd(transition: Transition?) {
                    transition?.removeListener(this)
                }

                override fun onTransitionCancel(transition: Transition?) {
                    transition?.removeListener(this)
                }

                override fun onTransitionPause(transition: Transition?) = Unit

                override fun onTransitionResume(transition: Transition?) = Unit
            })
        }
    }

    companion object {
        const val BOOK_ISBN = "BOOK_ISBN"
        const val BOOK_IMAGE_URL = "BOOK_IMAGE_URL"

        const val VIEW_NAME_IMAGE = "detail:image"
    }
}