package com.inhouse.cleannytimesapp.ui.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@BindingAdapter("app:refreshing")
fun SwipeRefreshLayout.refreshing(flow: Flow<ArticleListViewModel.ViewState>) {
    CoroutineScope(Dispatchers.Main).launch {
        flow.collectLatest {
            isRefreshing = it.isLoading
        }
    }
}
