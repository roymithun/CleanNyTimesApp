package com.inhouse.cleannytimesapp.model

import android.os.Parcelable
import com.inhouse.cleannytimesapp.base.model.ItemMapper
import com.inhouse.cleannytimesapp.base.model.ModelItem
import com.inhouse.cleannytimesapp.domain.model.Article
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class ArticleItem(
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
    val mediaList: List<MediaItem>
) : ModelItem(), Parcelable


class ArticleItemMapper @Inject constructor(
    private val mediaItemMapper: MediaItemMapper
) : ItemMapper<Article, ArticleItem> {
    override fun mapToPresentation(model: Article) = ArticleItem(
        id = model.id,
        url = model.url,
        source = model.source,
        publishedDate = model.publishedDate,
        updatedDate = model.updatedDate,
        section = model.section,
        subSection = model.subSection,
        byLine = model.byLine,
        title = model.title,
        abstractContent = model.abstractContent,
        desFacetList = model.desFacetList,
        mediaList = model.mediaList.map { mediaItemMapper.mapToPresentation(it) }
    )

    override fun mapToDomain(modelItem: ArticleItem) = Article(
        id = modelItem.id,
        url = modelItem.url,
        source = modelItem.source,
        publishedDate = modelItem.publishedDate,
        updatedDate = modelItem.updatedDate,
        section = modelItem.section,
        subSection = modelItem.subSection,
        byLine = modelItem.byLine,
        title = modelItem.title,
        abstractContent = modelItem.abstractContent,
        desFacetList = modelItem.desFacetList,
        mediaList = modelItem.mediaList.map { mediaItemMapper.mapToDomain(it) }
    )
}