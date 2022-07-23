package com.inhouse.cleannytimesapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.cleannytimesapp.databinding.ListItemBinding
import com.inhouse.cleannytimesapp.model.ArticleItem

class ArticleListAdapter(private val clickListener: OnClickListener) :
    ListAdapter<ArticleItem, ArticleListAdapter.ArticleViewHolder>(ArticleItemDiffCallback()) {
    class ArticleViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleItem, clickListener: OnClickListener) {
            binding.article = article
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onClick(article: ArticleItem)
    }

    class ArticleItemDiffCallback : DiffUtil.ItemCallback<ArticleItem>() {
        override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean =
            oldItem.id == newItem.id

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}