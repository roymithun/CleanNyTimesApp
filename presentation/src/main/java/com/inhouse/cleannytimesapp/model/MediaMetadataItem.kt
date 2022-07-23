package com.inhouse.cleannytimesapp.model

import android.os.Parcelable
import com.inhouse.cleannytimesapp.base.ItemMapper
import com.inhouse.cleannytimesapp.base.ModelItem
import com.inhouse.cleannytimesapp.domain.model.MediaMetadata
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class MediaMetadataItem(
    val id: Int? = null,
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : ModelItem(), Parcelable

class MediaMetadataItemMapper @Inject constructor() : ItemMapper<MediaMetadata, MediaMetadataItem> {
    override fun mapToPresentation(model: MediaMetadata) = MediaMetadataItem(
        id = model.id,
        url = model.url,
        format = model.format,
        height = model.height,
        width = model.width
    )

    override fun mapToDomain(modelItem: MediaMetadataItem) = MediaMetadata(
        id = modelItem.id,
        url = modelItem.url,
        format = modelItem.format,
        height = modelItem.height,
        width = modelItem.width
    )
}