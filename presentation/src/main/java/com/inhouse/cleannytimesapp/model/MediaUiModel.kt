package com.inhouse.cleannytimesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaUiModel(
    val id: Int? = null,
    val type: String,
    val subType: String,
    val caption: String,
    val copyright: String,
    val mediaMetadataList: List<MediaMetadataUiModel>
) : Parcelable
