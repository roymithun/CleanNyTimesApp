package com.inhouse.cleannytimesapp.domain.model

data class Media(
    val id: Int? = null,
    val type: String,
    val subType: String,
    val caption: String,
    val copyright: String,
    val mediaMetadataList: List<MediaMetadata>
) : Model()
