package com.yeonkyu.booksearchapp.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.yeonkyu.booksearchapp.R
import com.yeonkyu.booksearchapp.databinding.ActivitySearchBinding
import com.yeonkyu.booksearchapp.ui.detail.DetailActivity
import com.yeonkyu.booksearchapp.util.RecyclerViewPager
import com.yeonkyu.booksearchapp.util.makeDialog
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
        observeDialogEvent()
    }

    private fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun setUpView() {
        binding.searchBt.setOnClickListener {
            val keyword = binding.searchEt.text.toString()
            search(keyword)
        }
        binding.searchEraseBt.setOnClickListener {
            binding.searchEt.setText("")
        }
        binding.searchEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.searchEt.text.toString()
                search(keyword)
                handled = true
            }
            return@setOnEditorActionListener handled
        }
    }

    private fun setUpListAdapter() {
        bookAdapter = BookAdapter { view, book ->
            //todo
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.BOOK_ISBN, book.id)
            }
            startActivity(intent)
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
            if (it.isEmpty()) {
                binding.searchExplainTxt.text = getString(R.string.search_empty_result)
            } else {
                binding.searchExplainTxt.text = ""
            }
        }
    }

    private fun observeDialogEvent() {
        viewModel.dialogEvent.observe(this) {
            makeDialog(it, null)
        }
    }

    private fun search(keyword: String) {
        viewModel.resetBookList()
        pager.resetPage()
        viewModel.fetchNextBookList(keyword, 1)
    }
}