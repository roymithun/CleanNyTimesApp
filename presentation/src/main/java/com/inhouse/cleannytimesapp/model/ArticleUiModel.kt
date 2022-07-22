package com.inhouse.cleannytimesapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleUiModel(
    val id: Long,
    val url: String,
    val source: String,
    val publishedDate: String,
    val updatedDate: String,
    val section: String,
    val subSection: String,
    val byLine: String,
    val title: String,
    val desFacetList: List<String>,
    val mediaList: List<MediaUiModel>
) : Parcelable