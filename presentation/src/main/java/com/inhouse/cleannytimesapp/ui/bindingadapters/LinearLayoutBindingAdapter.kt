package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.inhouse.cleannytimesapp.ui.main.ArticleListViewModel

@BindingAdapter("android:visibility")
fun LinearLayout.setVisibility(state: ArticleListViewModel.ViewState) {
    visibility = if (state.isError) View.VISIBLE else View.GONE
}