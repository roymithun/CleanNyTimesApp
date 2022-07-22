package com.inhouse.cleannytimesapp.domain.model

data class ArticleList(
    val status: String,
    val numResults: Int,
    val results: List<Article>
) : Model()