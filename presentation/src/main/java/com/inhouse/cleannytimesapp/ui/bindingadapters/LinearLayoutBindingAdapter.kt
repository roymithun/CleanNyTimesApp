package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@BindingAdapter("android:visibility")
fun LinearLayout.setVisibility(flow: Flow<ArticleListViewModel.ViewState>) {
    CoroutineScope(Dispatchers.Main).launch {
        flow.collectLatest {
            visibility = if (it.isError) View.VISIBLE else View.GONE
        }
    }
}
