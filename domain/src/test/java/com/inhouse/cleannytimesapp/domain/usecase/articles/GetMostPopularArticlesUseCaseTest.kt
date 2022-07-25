package com.inhouse.cleannytimesapp.domain.usecase.articles

import com.google.common.truth.Truth.assertThat
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.repository.ArticlesRepository
import com.inhouse.cleannytimesapp.domain.util.FakeArticlesData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMostPopularArticlesUseCaseTest {
    private lateinit var getMostPopularArticlesUseCase: GetMostPopularArticlesUseCase

    @MockK
    lateinit var articlesRepository: ArticlesRepository

    @MockK(relaxed = true)
    lateinit var params: GetMostPopularArticlesUseCase.Params

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getMostPopularArticlesUseCase =
            GetMostPopularArticlesUseCase(articlesRepository, UnconfinedTestDispatcher())
    }

    @Test
    fun `assert invoke returns list of articles`() {
        // given
        val articles = FakeArticlesData.articles
        coEvery { articlesRepository.getArticles(any(), any()) } returns articles

        // when
        val result = runBlocking {
            getMostPopularArticlesUseCase.invoke(parameters = params)
        }

        // then
        assertThat(result).isEqualTo(Result.Success(articles))
    }
}