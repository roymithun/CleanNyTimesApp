package com.inhouse.cleannytimesapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inhouse.cleannytimesapp.data.model.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticleList(articleList: List<ArticleEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity): Long

    @Query("SELECT * FROM article WHERE article.title LIKE :filter")
    suspend fun getAllArticles(filter: String = "%%"): List<ArticleEntity>

    @Query("SELECT * FROM article WHERE article.id = :id")
    suspend fun getArticle(id: Long): ArticleEntity?
}
