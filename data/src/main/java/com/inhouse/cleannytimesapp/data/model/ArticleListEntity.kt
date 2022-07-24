package com.inhouse.cleannytimesapp.data.model

import com.google.gson.annotations.SerializedName
import com.inhouse.cleannytimesapp.data.base.EntityMapper
import com.inhouse.cleannytimesapp.data.base.ModelEntity
import com.inhouse.cleannytimesapp.domain.model.ArticleList
import javax.inject.Inject

data class ArticleListEntity(
    val status: String,
    @SerializedName("num_results") val numResults: Int,
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