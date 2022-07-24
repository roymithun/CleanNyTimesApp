package com.inhouse.cleannytimesapp.domain.model

data class Article(
    val id: Long,
    val url: String,
    val source: String,
    val publishedDate: String,
    val updatedDate: String,
    val section: String,
    val subSection: String,
    val byLine: String,
    val title: String,
    val abstractContent: String,
    val desFacetList: List<String>,
    val mediaList: List<Media>
) : Model()