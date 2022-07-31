package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel
import com.inhouse.cleannytimesapp.ui.main.adapter.ArticleListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@BindingAdapter("refreshData")
fun RecyclerView.refreshData(articleList: List<ArticleItem>?) {
    articleList?.let {
        if (adapter is ArticleListAdapter) {
            (adapter as ArticleListAdapter).submitList(it)
        }
    }
}

@BindingAdapter("refreshDataWithState")
fun RecyclerView.refreshDataWithState(flow: Flow<ArticleListViewModel.ViewState>) {
    CoroutineScope(Dispatchers.Main).launch {
        flow.collectLatest {
            it.articles.let {
                if (adapter is ArticleListAdapter) {
                    (adapter as ArticleListAdapter).submitList(it)
                }
            }
        }
    }
}

@BindingAdapter("android:visibility")
fun RecyclerView.setVisibility(flow: Flow<ArticleListViewModel.ViewState>) {
    CoroutineScope(Dispatchers.Main).launch {
        flow.collectLatest {
            visibility = if (it.isError || it.articles.isEmpty()) View.GONE else View.VISIBLE
        }
    }
}