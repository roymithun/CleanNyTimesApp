package com.inhouse.cleannytimesapp.model

import android.os.Parcelable
import com.inhouse.cleannytimesapp.base.ItemMapper
import com.inhouse.cleannytimesapp.base.ModelItem
import com.inhouse.cleannytimesapp.domain.model.Media
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class MediaItem(
    val id: Int? = null,
    val type: String,
    val subType: String,
    val caption: String,
    val copyright: String,
    val mediaMetadataList: List<MediaMetadataItem>
) : ModelItem(), Parcelable

class MediaItemMapper @Inject constructor(
    private val mediaMetadataItemMapper: MediaMetadataItemMapper
) : ItemMapper<Media, MediaItem> {
    override fun mapToPresentation(model: Media) = MediaItem(
        id = model.id,
        type = model.type,
        subType = model.subType,
        caption = model.caption,
        copyright = model.copyright,
        mediaMetadataList = model.mediaMetadataList.map {
            mediaMetadataItemMapper.mapToPresentation(
                it
            )
        }
    )

    override fun mapToDomain(modelItem: MediaItem) = Media(
        id = modelItem.id,
        type = modelItem.type,
        subType = modelItem.subType,
        caption = modelItem.caption,
        copyright = modelItem.copyright,
        mediaMetadataList = modelItem.mediaMetadataList.map {
            mediaMetadataItemMapper.mapToDomain(
                it
            )
        }
    )
}