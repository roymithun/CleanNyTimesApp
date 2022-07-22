package com.inhouse.cleannytimesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaMetadataUiModel(
    val id: Int? = null,
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : Parcelable