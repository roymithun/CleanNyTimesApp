package com.inhouse.cleannytimesapp.domain.usecase.articles

import com.inhouse.cleannytimesapp.domain.di.IoDispatcher
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import com.inhouse.cleannytimesapp.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMostPopularArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) :
    UseCase<GetMostPopularArticlesUseCase.Params, List<Article>>(coroutineDispatcher) {

    data class Params(val period: Int, val apiKey: String)

    override suspend fun execute(parameters: Params): List<Article> {
        return articlesRepository.getArticles(parameters.period, parameters.apiKey)
    }
}