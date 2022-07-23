package com.inhouse.cleannytimesapp.ui.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.ui.main.adapter.ArticleListAdapter

@BindingAdapter("refreshData")
fun RecyclerView.refreshData(articleList: List<ArticleItem>?) {
    articleList?.let {
        if (adapter is ArticleListAdapter) {
            (adapter as ArticleListAdapter).submitList(it)
        }
    }
}