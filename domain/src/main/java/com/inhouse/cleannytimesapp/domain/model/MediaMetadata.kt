package com.inhouse.cleannytimesapp.domain.model

data class MediaMetadata(
    val id: Int? = null,
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : Model()