package com.inhouse.cleannytimesapp.data.repository

import com.inhouse.cleannytimesapp.data.model.ArticleEntityMapper
import com.inhouse.cleannytimesapp.data.remote.api.ArticleListApi
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import javax.inject.Inject

class MostPopularArticlesRepository @Inject constructor(
    private val articleListApi: ArticleListApi,
    private val articleEntityMapper: ArticleEntityMapper
) : ArticlesRepository {
    override suspend fun getArticles(period: Int, apiKey: String): List<Article> {
        return articleListApi.getArticleList(
            period,
            apiKey
        ).results.map { articleEntityMapper.mapToDomain(it) }
    }
}