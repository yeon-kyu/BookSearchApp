package com.yeonkyu.booksearchapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yeonkyu.booksearchapp.R
import com.yeonkyu.booksearchapp.data.model.Book
import com.yeonkyu.booksearchapp.databinding.ItemBookBinding

class BookAdapter constructor(
    private val itemClick: (View, Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(
    BookDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        val binding = ItemBookBinding.bind(view)
        return BookViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class BookViewHolder constructor(
        private val binding: ItemBookBinding,
        private val itemClick: (View, Book) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                getItem(adapterPosition)?.let {
                    itemClick(itemView, it)
                }
            }
        }

        fun bind(book: Book) {
            binding.bookItem = book
            binding.executePendingBindings()
        }
    }

    object BookDiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }
    }
}