package com.inhouse.cleannytimesapp.data.repository

import com.inhouse.cleannytimesapp.data.local.db.ArticleDao
import com.inhouse.cleannytimesapp.data.model.ArticleEntityMapper
import com.inhouse.cleannytimesapp.data.model.MediaEntityMapper
import com.inhouse.cleannytimesapp.data.model.MediaMetadataEntityMapper
import com.inhouse.cleannytimesapp.data.remote.api.ArticleListApi
import com.inhouse.cleannytimesapp.data.util.FakeArticlesData
import com.inhouse.cleannytimesapp.domain.model.Article
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class MostPopularArticlesRepositoryTest {
    @MockK
    private lateinit var articleListApi: ArticleListApi

    private lateinit var articleEntityMapper: ArticleEntityMapper

    @MockK
    private lateinit var articleDao: ArticleDao

    private lateinit var mostPopularArticlesRepository: MostPopularArticlesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        articleEntityMapper = ArticleEntityMapper(MediaEntityMapper(MediaMetadataEntityMapper()))
        mostPopularArticlesRepository = MostPopularArticlesRepository(
            articleListApi,
            articleEntityMapper,
            articleDao
        )
    }

    @Test
    fun `assert list of articles from getArticles is correctly mapped to domain model`() {
        // given
        coEvery {
            articleListApi.getArticleList(
                any(),
                any()
            )
        } returns FakeArticlesData.articleListEntity

        coEvery {
            articleDao.insertArticleList(any())
        } returns FakeArticlesData.articleEntities.map { it.id }

        // when
        var articles: List<Article>
        runBlocking {
            articles = mostPopularArticlesRepository.getArticles(7, "apiKey")
        }

        // then
        articles shouldBeEqualTo FakeArticlesData.articles
    }

    @Test
    fun `assert list of articles from searchArticles is correctly mapped to domain model`() {
        // given
        coEvery {
            articleDao.getAllArticles(any())
        } returns FakeArticlesData.articleEntities

        // when
        var articles: List<Article>
        runBlocking {
            articles = mostPopularArticlesRepository.searchArticles("some_query")
        }

        // then
        articles shouldBeEqualTo FakeArticlesData.articles
    }
}