package com.inhouse.cleannytimesapp.data.local.db

import com.inhouse.cleannytimesapp.data.utils.FakeArticlesData
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class ArticleDaoTest : DbTest() {
    @Test
    fun testInsertAndGet() {
        val articleEntityList = FakeArticlesData.articles
        runBlocking {
            val idListInsert = db.articleDao().insertArticleList(articleEntityList)
            val allArticles = db.articleDao().getAllArticles()
            val idListGet = allArticles.map { it.id }
            idListInsert shouldBeEqualTo idListGet
        }
    }

    @Test
    fun findNotExists() {
        val articleEntityList = FakeArticlesData.articles
        val articleId = ArgumentMatchers.anyLong()
        runBlocking {
            db.articleDao().insertArticleList(articleEntityList)
            val article = db.articleDao().getArticle(articleId)
            article shouldBeEqualTo null
        }
    }
}