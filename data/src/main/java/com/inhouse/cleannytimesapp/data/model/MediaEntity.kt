package com.inhouse.cleannytimesapp.data.model

import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.domain.model.Media
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class MediaEntity(
    val id: Int? = null,
    val type: String,
    @Json(name = "subtype") val subType: String,
    val caption: String,
    val copyright: String,
    @Json(name = "media-metadata") val mediaMetadataList: List<MediaMetadataEntity>
) : ModelEntity()

class MediaEntityMapper @Inject constructor(
    private val mediaMetadataEntityMapper: MediaMetadataEntityMapper
) : EntityMapper<Media, MediaEntity> {
    override fun mapToDomain(entity: MediaEntity) = Media(
        id = entity.id,
        type = entity.type,
        subType = entity.subType,
        caption = entity.caption,
        copyright = entity.copyright,
        mediaMetadataList = entity.mediaMetadataList.map { mediaMetadataEntityMapper.mapToDomain(it) }
    )

    override fun mapToEntity(model: Media) = MediaEntity(
        id = model.id,
        type = model.type,
        subType = model.subType,
        caption = model.caption,
        copyright = model.copyright,
        mediaMetadataList = model.mediaMetadataList.map { mediaMetadataEntityMapper.mapToEntity(it) }
    )
}