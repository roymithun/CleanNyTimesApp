package com.inhouse.cleannytimesapp.ui.bindingadapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.inhouse.cleannytimesapp.R
import com.inhouse.cleannytimesapp.model.MediaItem

@BindingAdapter("mediaList", "placeholder")
fun ImageView.loadThumbnailFromMediaList(mediaList: List<MediaItem>, placeholder: Drawable) {
    val url: String =
        if (mediaList.isEmpty()) "invalid" else mediaList.firstOrNull()?.mediaMetadataList?.firstOrNull()?.url
            ?: "empty list"
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_broken_image)
        .placeholder(placeholder)
        .transform(CircleCrop())
        .into(this)
}

@BindingAdapter("articlePhoto", "placeholder")
fun ImageView.loadArticleDetailPhoto(url: String, placeholder: Drawable) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.ic_broken_image)
        .placeholder(placeholder)
        .into(this)
}
