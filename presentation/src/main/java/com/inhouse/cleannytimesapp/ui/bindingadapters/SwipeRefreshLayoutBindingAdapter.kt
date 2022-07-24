package com.inhouse.cleannytimesapp.ui.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel
import kotlinx.coroutines.flow.StateFlow

@BindingAdapter("app:refreshing")
fun SwipeRefreshLayout.refreshing(state: ArticleListViewModel.ViewState) {
    isRefreshing = state.isLoading
}