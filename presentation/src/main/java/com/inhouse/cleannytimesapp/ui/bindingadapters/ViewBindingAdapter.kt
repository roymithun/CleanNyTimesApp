package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean?) {
    visible?.let {
        visibility = if (it) View.VISIBLE else View.GONE
    } ?: run {
        visibility = View.GONE
    }
}
