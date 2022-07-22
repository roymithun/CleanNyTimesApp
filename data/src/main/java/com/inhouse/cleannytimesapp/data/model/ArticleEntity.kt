package com.inhouse.cleannytimesapp.data.model

import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.domain.model.Article
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class ArticleEntity(
    val id: Long,
    val url: String,
    val source: String,
    @Json(name = "published_date") val publishedDate: String,
    @Json(name = "updated") val updatedDate: String,
    val section: String,
    @Json(name = "subsection") val subSection: String,
    @Json(name = "byline") val byLine: String,
    val title: String,
    @Json(name = "des_facet") val desFacetList: List<String>,
    @Json(name = "media") val mediaList: List<MediaEntity>
) : ModelEntity()

class ArticleEntityMapper @Inject constructor(
    private val mediaEntityMapper: MediaEntityMapper
) : EntityMapper<Article, ArticleEntity> {
    override fun mapToDomain(entity: ArticleEntity) = Article(
        id = entity.id,
        url = entity.url,
        source = entity.source,
        publishedDate = entity.publishedDate,
        updatedDate = entity.publishedDate,
        section = entity.section,
        subSection = entity.subSection,
        byLine = entity.byLine,
        title = entity.title,
        desFacetList = entity.desFacetList,
        mediaList = entity.mediaList.map { mediaEntityMapper.mapToDomain(it) }
    )

    override fun mapToEntity(model: Article) = ArticleEntity(
        id = model.id,
        url = model.url,
        source = model.source,
        publishedDate = model.publishedDate,
        updatedDate = model.publishedDate,
        section = model.section,
        subSection = model.subSection,
        byLine = model.byLine,
        title = model.title,
        desFacetList = model.desFacetList,
        mediaList = model.mediaList.map { mediaEntityMapper.mapToEntity(it) }
    )
}