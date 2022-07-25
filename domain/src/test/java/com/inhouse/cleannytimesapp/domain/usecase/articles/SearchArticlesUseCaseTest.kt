package com.inhouse.cleannytimesapp.domain.usecase.articles

import com.google.common.truth.Truth
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import com.inhouse.cleannytimesapp.domain.util.FakeArticlesData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class SearchArticlesUseCaseTest {
    private lateinit var searchArticlesUseCase: SearchArticlesUseCase

    @MockK
    lateinit var articlesRepository: ArticlesRepository

    @MockK
    lateinit var params: SearchArticlesUseCase.Params

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchArticlesUseCase =
            SearchArticlesUseCase(articlesRepository, UnconfinedTestDispatcher())
    }

    @Test
    fun `assert search returns list of articles`() {
        // given
        every { params.query } returns "%Ivana%"
        val articles = FakeArticlesData.articles
        coEvery { articlesRepository.searchArticles(any()) } returns listOf(articles[2])

        // when
        val result = runBlocking {
            searchArticlesUseCase.invoke(parameters = params)
        }

        // then
        Truth.assertThat(result).isEqualTo(Result.Success(listOf(articles[2])))
    }
}