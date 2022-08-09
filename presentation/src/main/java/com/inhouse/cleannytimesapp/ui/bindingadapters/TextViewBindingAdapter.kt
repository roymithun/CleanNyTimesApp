package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@BindingAdapter("formattedPublishedDate")
fun TextView.publishedDate(dateStr: String) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd") // 2022-07-16
    val parsedDate = simpleDateFormat.parse(dateStr)
    val newDateFormat = SimpleDateFormat("MMM dd, yyyy")
    text = newDateFormat.format(parsedDate)
}

@BindingAdapter("android:visibility")
fun TextView.setVisibility(flow: Flow<ArticleListViewModel.ViewState>) {
    CoroutineScope(Dispatchers.Main).launch {
        flow.collectLatest {
            visibility = if (!it.isError && it.articles.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}
