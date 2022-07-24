package com.inhouse.cleannytimesapp.data.repository

import com.inhouse.cleannytimesapp.data.local.db.ArticleDao
import com.inhouse.cleannytimesapp.data.model.ArticleEntity
import com.inhouse.cleannytimesapp.data.model.ArticleEntityMapper
import com.inhouse.cleannytimesapp.data.remote.api.ArticleListApi
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class MostPopularArticlesRepository @Inject constructor(
    private val articleListApi: ArticleListApi,
    private val articleEntityMapper: ArticleEntityMapper,
    private val articleDao: ArticleDao
) : ArticlesRepository {
    override suspend fun getArticles(period: Int, apiKey: String): List<Article> {
        return try {
            val articleEntityList: List<ArticleEntity> = articleListApi.getArticleList(
                period,
                apiKey
            )?.results ?: emptyList()

            articleEntityList.let {
//                Timber.d("gibow articleDao: $articleDao")
                val insertArticleList: List<Long> = articleDao.insertArticleList(it)
                Timber.d("gibow insertArticleList = $insertArticleList")
            }
            articleEntityList.map {
                articleEntityMapper.mapToDomain(it)
            }
        } catch (e: UnknownHostException) {
            articleDao.getAllArticles().map { articleEntityMapper.mapToDomain(it) }
        } catch (e: Exception) {
            Timber.e("exception: ${e.message}")
            emptyList()
        }
    }
}