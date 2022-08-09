package com.inhouse.cleannytimesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.domain.model.MediaMetadata
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@Entity(tableName = "media_metadata")
@JsonClass(generateAdapter = true)
data class MediaMetadataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
) : ModelEntity()

class MediaMetadataEntityMapper @Inject constructor() :
    EntityMapper<MediaMetadata, MediaMetadataEntity> {
    override fun mapToDomain(entity: MediaMetadataEntity) = MediaMetadata(
        id = entity.id,
        url = entity.url,
        format = entity.format,
        height = entity.height,
        width = entity.width
    )

    override fun mapToEntity(model: MediaMetadata) = MediaMetadataEntity(
        id = model.id,
        url = model.url,
        format = model.format,
        height = model.height,
        width = model.width
    )
}
