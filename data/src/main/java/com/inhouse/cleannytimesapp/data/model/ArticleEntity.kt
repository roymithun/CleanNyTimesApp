package com.inhouse.cleannytimesapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.data.local.db.RoomTypeConverters
import com.inhouse.cleannytimesapp.domain.model.Article
import javax.inject.Inject

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey
    val id: Long,
    val url: String,
    val source: String,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("updated") val updatedDate: String,
    val section: String,
    @SerializedName("subsection") val subSection: String,
    @SerializedName("byline") val byLine: String,
    val title: String,
    @SerializedName("abstract") val abstractContent: String,
    @TypeConverters(RoomTypeConverters::class)
    @SerializedName("des_facet")
    val desFacetList: List<String>,
    @TypeConverters(RoomTypeConverters::class)
    @SerializedName("media")
    val mediaList: List<MediaEntity>
) : ModelEntity()

class ArticleEntityMapper @Inject constructor(
    private val mediaEntityMapper: MediaEntityMapper
) : EntityMapper<Article, ArticleEntity> {
    override fun mapToDomain(entity: ArticleEntity) = Article(
        id = entity.id,
        url = entity.url,
        source = entity.source,
        publishedDate = entity.publishedDate,
        updatedDate = entity.updatedDate,
        section = entity.section,
        subSection = entity.subSection,
        byLine = entity.byLine,
        title = entity.title,
        abstractContent = entity.abstractContent,
        desFacetList = entity.desFacetList,
        mediaList = entity.mediaList.map { mediaEntityMapper.mapToDomain(it) }
    )

    override fun mapToEntity(model: Article) = ArticleEntity(
        id = model.id,
        url = model.url,
        source = model.source,
        publishedDate = model.publishedDate,
        updatedDate = model.updatedDate,
        section = model.section,
        subSection = model.subSection,
        byLine = model.byLine,
        title = model.title,
        abstractContent = model.abstractContent,
        desFacetList = model.desFacetList,
        mediaList = model.mediaList.map { mediaEntityMapper.mapToEntity(it) }
    )
}
