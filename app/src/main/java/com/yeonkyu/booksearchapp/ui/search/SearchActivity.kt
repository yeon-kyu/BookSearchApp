package com.yeonkyu.booksearchapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonkyu.booksearchapp.R
import com.yeonkyu.booksearchapp.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpListAdapter()
        observeData()

        viewModel.searchByKeyword("MongoDB")
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
    }

    private fun setUpListAdapter() {
        bookAdapter = BookAdapter { view, book ->
            //todo
        }
        binding.recyclerview.apply {
            adapter = bookAdapter
            layoutManager = GridLayoutManager(
                this@SearchActivity,
                2
            )
        }
    }

    private fun observeData() {
        viewModel.bookList.observe(this) {
            bookAdapter.submitList(it)
        }
    }
}