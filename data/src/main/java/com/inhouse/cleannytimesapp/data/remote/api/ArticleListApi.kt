package com.inhouse.cleannytimesapp.data.remote.api

import com.inhouse.cleannytimesapp.data.model.ArticleListEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleListApi {
    @GET("all-sections/{period}.json")
    suspend fun getArticleList(
        @Path("period") period: Int = 7, // weekly
        @Query("api-key") apiKey: String
    ): ArticleListEntity?
}