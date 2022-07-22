package com.inhouse.cleannytimesapp.data.model

import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.domain.model.ArticleList
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
data class ArticleListEntity(
    val status: String,
    @Json(name = "num_results") val numResults: Int,
    val results: List<ArticleEntity>
) : ModelEntity()

class ArticleListEntityMapper @Inject constructor(
    private val articleEntityMapper: ArticleEntityMapper
) : EntityMapper<ArticleList, ArticleListEntity> {
    override fun mapToDomain(entity: ArticleListEntity) = ArticleList(
        status = entity.status,
        numResults = entity.numResults,
        results = entity.results.map { articleEntityMapper.mapToDomain(it) }
    )

    override fun mapToEntity(model: ArticleList) = ArticleListEntity(
        status = model.status,
        numResults = model.numResults,
        results = model.results.map { articleEntityMapper.mapToEntity(it) }
    )
}