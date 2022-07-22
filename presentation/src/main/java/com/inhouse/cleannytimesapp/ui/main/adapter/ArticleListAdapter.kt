package com.inhouse.cleannytimesapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.cleannytimesapp.databinding.ListItemBinding
import com.inhouse.cleannytimesapp.model.ArticleUiModel

class ArticleListAdapter(private val clickListener: OnClickListener) :
    ListAdapter<ArticleUiModel, ArticleListAdapter.ArticleViewHolder>(ArticleItemDiffCallback()) {
    class ArticleViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticleUiModel, clickListener: OnClickListener) {
            binding.article = article
            binding.clickListener = clickListener
            val mediaList = article.mediaList
            if (mediaList.isNotEmpty()) {
                val mediaMetadataList = mediaList[0].mediaMetadataList
                if (mediaMetadataList.isNotEmpty()) {
                    val thumbnailImgUrl = mediaMetadataList[0].url
                    /*binding.ivThumbnail.load(thumbnailImgUrl) {
                        placeholder(R.drawable.loading_img)
                        error(R.drawable.ic_broken_image)
                        transformations(CircleCropTransformation())
                    }*/
                }
            } else {
//                binding.ivThumbnail.load(R.drawable.ic_broken_image)
            }
            binding.tvDate.text = article.publishedDate
            binding.executePendingBindings()
        }
    }

    interface OnClickListener {
        fun onClick(article: ArticleUiModel)
    }

    class ArticleItemDiffCallback : DiffUtil.ItemCallback<ArticleUiModel>() {
        override fun areItemsTheSame(oldItem: ArticleUiModel, newItem: ArticleUiModel): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ArticleUiModel, newItem: ArticleUiModel): Boolean =
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