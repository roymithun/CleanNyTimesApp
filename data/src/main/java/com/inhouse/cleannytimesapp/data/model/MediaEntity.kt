package com.inhouse.cleannytimesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.domain.model.Media
import javax.inject.Inject

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val type: String,
    @SerializedName("subtype") val subType: String,
    val caption: String,
    val copyright: String,
    @SerializedName("media-metadata") val mediaMetadataList: List<MediaMetadataEntity>
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
