package com.yeonkyu.booksearchapp.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
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
        if (isbn == null) {
            makeDialog("책의 isbn 을 가오지 못했습니다.", null)
        } else {
            viewModel.getBookInfo(isbn)
        }

        binding.detailCloseBt.setOnClickListener {
            finish()
        }
    }

    private fun observeDialogEvent() {
        viewModel.dialogEvent.observe(this) {
            makeDialog(it, null)
        }
    }

    companion object {
        const val BOOK_ISBN = "BOOK_ISBN"
    }
}