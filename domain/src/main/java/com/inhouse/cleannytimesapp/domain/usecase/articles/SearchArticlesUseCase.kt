package com.inhouse.cleannytimesapp.domain.usecase.articles

import com.inhouse.cleannytimesapp.domain.di.IoDispatcher
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import com.inhouse.cleannytimesapp.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) :
    UseCase<SearchArticlesUseCase.Params, List<Article>>(coroutineDispatcher) {
    override suspend fun execute(parameters: Params): List<Article> {
        return articlesRepository.searchArticles(parameters.query)
    }

    data class Params(val query: String)
}
