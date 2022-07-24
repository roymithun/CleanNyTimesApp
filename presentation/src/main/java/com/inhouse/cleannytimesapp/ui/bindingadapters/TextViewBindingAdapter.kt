package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.inhouse.cleannytimesapp.R
import java.text.SimpleDateFormat

@BindingAdapter("formattedPublishedDate")
fun TextView.publishedDate(dateStr: String) {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")//2022-07-16
    val parsedDate = simpleDateFormat.parse(dateStr)
    val newDateFormat = SimpleDateFormat("MMM dd, yyyy")
    text = newDateFormat.format(parsedDate)
}