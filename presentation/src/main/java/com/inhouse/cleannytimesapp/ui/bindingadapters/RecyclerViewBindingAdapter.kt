package com.inhouse.cleannytimesapp.ui.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel
import com.inhouse.cleannytimesapp.ui.main.adapter.ArticleListAdapter

@BindingAdapter("refreshData")
fun RecyclerView.refreshData(articleList: List<ArticleItem>?) {
    articleList?.let {
        if (adapter is ArticleListAdapter) {
            (adapter as ArticleListAdapter).submitList(it)
        }
    }
}

@BindingAdapter("refreshDataWithState")
fun RecyclerView.refreshDataWithState(state: ArticleListViewModel.ViewState) {
    state.articles.let {
        if (adapter is ArticleListAdapter) {
            (adapter as ArticleListAdapter).submitList(it)
        }
    }
}