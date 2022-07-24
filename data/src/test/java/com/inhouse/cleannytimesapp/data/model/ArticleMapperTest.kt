package com.inhouse.cleannytimesapp.data.model

import com.inhouse.cleannytimesapp.data.utils.FakeArticlesData
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ArticleMapperTest {
    lateinit var articleEntityMapper: ArticleEntityMapper
    lateinit var mediaEntityMapper: MediaEntityMapper
    lateinit var mediaMetadataEntityMapper: MediaMetadataEntityMapper

    @Before
    fun setUp() {
        mediaMetadataEntityMapper = MediaMetadataEntityMapper()
        mediaEntityMapper = MediaEntityMapper(mediaMetadataEntityMapper)
        articleEntityMapper = ArticleEntityMapper(mediaEntityMapper)
    }

    @Test
    fun mapEntityToDomainTest() {
        val articleEntity = FakeArticlesData.articleEntities[0]
        val article = articleEntityMapper.mapToDomain(articleEntity)

        articleEntity.id shouldBeEqualTo article.id
        articleEntity.source shouldBeEqualTo article.source
        articleEntity.url shouldBeEqualTo article.url
        articleEntity.publishedDate shouldBeEqualTo article.publishedDate
        articleEntity.updatedDate shouldBeEqualTo article.updatedDate
        articleEntity.section shouldBeEqualTo article.section
        articleEntity.subSection shouldBeEqualTo article.subSection
        articleEntity.byLine shouldBeEqualTo article.byLine
        articleEntity.title shouldBeEqualTo article.title
        articleEntity.abstractContent shouldBeEqualTo article.abstractContent
        articleEntity.desFacetList shouldBeEqualTo article.desFacetList

        articleEntity.mediaList.map { it.copyright } shouldBeEqualTo article.mediaList.map { it.copyright }
        articleEntity.mediaList[0].mediaMetadataList.map { it.url } shouldBeEqualTo article.mediaList[0].mediaMetadataList.map { it.url }
    }

    @Test
    fun mapDomainToEntityTest() {
        val article = FakeArticlesData.articles[0]
        val articleEntity = articleEntityMapper.mapToEntity(article)

        articleEntity.id shouldBeEqualTo article.id
        articleEntity.source shouldBeEqualTo article.source
        articleEntity.url shouldBeEqualTo article.url
        articleEntity.publishedDate shouldBeEqualTo article.publishedDate
        articleEntity.updatedDate shouldBeEqualTo article.updatedDate
        articleEntity.section shouldBeEqualTo article.section
        articleEntity.subSection shouldBeEqualTo article.subSection
        articleEntity.byLine shouldBeEqualTo article.byLine
        articleEntity.title shouldBeEqualTo article.title
        articleEntity.abstractContent shouldBeEqualTo article.abstractContent
        articleEntity.desFacetList shouldBeEqualTo article.desFacetList

        articleEntity.mediaList.map { it.copyright } shouldBeEqualTo article.mediaList.map { it.copyright }
        articleEntity.mediaList[0].mediaMetadataList.map { it.url } shouldBeEqualTo article.mediaList[0].mediaMetadataList.map { it.url }
    }
}