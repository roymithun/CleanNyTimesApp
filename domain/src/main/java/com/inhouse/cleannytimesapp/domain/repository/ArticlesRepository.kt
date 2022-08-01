package com.inhouse.cleannytimesapp.domain.repository

import com.inhouse.cleannytimesapp.domain.model.Article

interface ArticlesRepository {
    suspend fun getArticles(period: Int, apiKey: String): List<Article>
    suspend fun searchArticles(query: String): List<Article>
}
