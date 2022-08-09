package com.inhouse.cleannytimesapp.model

import com.inhouse.cleannytimesapp.util.FakeArticlesData
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ArticleItemMapperTest {
    private lateinit var mediaMetadataItemMapper: MediaMetadataItemMapper
    private lateinit var mediaItemMapper: MediaItemMapper
    private lateinit var articleItemMapper: ArticleItemMapper

    @Before
    fun setUp() {
        mediaMetadataItemMapper = MediaMetadataItemMapper()
        mediaItemMapper = MediaItemMapper(mediaMetadataItemMapper)
        articleItemMapper = ArticleItemMapper(mediaItemMapper)
    }

    @Test
    fun mapDomainToPresentationTest() {
        val article = FakeArticlesData.articles[0]
        val articleItem = articleItemMapper.mapToPresentation(article)

        articleItem.id shouldBeEqualTo article.id
        articleItem.source shouldBeEqualTo article.source
        articleItem.url shouldBeEqualTo article.url
        articleItem.publishedDate shouldBeEqualTo article.publishedDate
        articleItem.updatedDate shouldBeEqualTo article.updatedDate
        articleItem.section shouldBeEqualTo article.section
        articleItem.subSection shouldBeEqualTo article.subSection
        articleItem.byLine shouldBeEqualTo article.byLine
        articleItem.title shouldBeEqualTo article.title
        articleItem.abstractContent shouldBeEqualTo article.abstractContent
        articleItem.desFacetList shouldBeEqualTo article.desFacetList

        articleItem.mediaList.map { it.copyright } shouldBeEqualTo article.mediaList.map { it.copyright }
        articleItem.mediaList[0].mediaMetadataList.map { it.url } shouldBeEqualTo article.mediaList[0].mediaMetadataList.map { it.url }
    }

    @Test
    fun mapDomainToEntityTest() {
        val articleItem = FakeArticlesData.articleItems[0]
        val article = articleItemMapper.mapToDomain(articleItem)

        articleItem.id shouldBeEqualTo article.id
        articleItem.source shouldBeEqualTo article.source
        articleItem.url shouldBeEqualTo article.url
        articleItem.publishedDate shouldBeEqualTo article.publishedDate
        articleItem.updatedDate shouldBeEqualTo article.updatedDate
        articleItem.section shouldBeEqualTo article.section
        articleItem.subSection shouldBeEqualTo article.subSection
        articleItem.byLine shouldBeEqualTo article.byLine
        articleItem.title shouldBeEqualTo article.title
        articleItem.abstractContent shouldBeEqualTo article.abstractContent
        articleItem.desFacetList shouldBeEqualTo article.desFacetList

        articleItem.mediaList.map { it.copyright } shouldBeEqualTo article.mediaList.map { it.copyright }
        articleItem.mediaList[0].mediaMetadataList.map { it.url } shouldBeEqualTo article.mediaList[0].mediaMetadataList.map { it.url }
    }
}
