package com.yeonkyu.booksearchapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.yeonkyu.booksearchapp.R
import com.yeonkyu.booksearchapp.databinding.ActivitySearchBinding
import com.yeonkyu.booksearchapp.util.RecyclerViewPager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var bookAdapter: BookAdapter
    private lateinit var pager: RecyclerViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpView()
        setUpListAdapter()
        observeData()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
    }

    private fun setUpView() {
        binding.searchCancelBt.setOnClickListener {
            val keyword = binding.searchEt.text.toString()
            viewModel.resetBookList()
            pager.resetPage()
            viewModel.fetchNextBookList(keyword, 1)
        }
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
            pager = RecyclerViewPager(
                recyclerView = this,
                isLoading = { viewModel.isLoading.value == true },
                loadNext = { nextPage -> viewModel.fetchNextBookList(null, nextPage) },
                isEnd = { viewModel.isEnd.value == true }
            )
        }
    }

    private fun observeData() {
        viewModel.bookList.observe(this) {
            bookAdapter.submitList(it.toList())
        }
    }
}