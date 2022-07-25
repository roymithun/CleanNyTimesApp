package com.inhouse.cleannytimesapp.ui.main

import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.usecase.articles.GetMostPopularArticlesUseCase
import com.inhouse.cleannytimesapp.domain.usecase.articles.SearchArticlesUseCase
import com.inhouse.cleannytimesapp.library.test.utils.MainCoroutineRule
import com.inhouse.cleannytimesapp.model.ArticleItemMapper
import com.inhouse.cleannytimesapp.navigation.NavManager
import com.inhouse.cleannytimesapp.util.FakeArticlesData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleListViewModelTest {
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @MockK
    lateinit var mockArticlesUseCase: GetMostPopularArticlesUseCase

    @MockK
    lateinit var mockSearchArticlesUseCase: SearchArticlesUseCase

    @MockK
    lateinit var mockArticleItemMapper: ArticleItemMapper

    @MockK(relaxed = true)
    lateinit var mockNavManager: NavManager

    @MockK(relaxed = true)
    lateinit var params: GetMostPopularArticlesUseCase.Params

    lateinit var viewModel: ArticleListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ArticleListViewModel(
            mockArticlesUseCase,
            mockSearchArticlesUseCase,
            mockArticleItemMapper,
            mockNavManager
        )
    }

    @Test
    fun `invoke getMostPopularArticlesUseCase`() {
        // given
        coEvery { mockArticlesUseCase.invoke(any()) } returns Result.Success(emptyList())

        // when
        viewModel.loadData()

        // then
        coVerify { mockArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `invoke searchArticlesUseCase`() {
        // given
        coEvery { mockArticlesUseCase.invoke(any()) } returns Result.Success(emptyList())

        // when
        viewModel.searchArticles(query = "")

        // then
        coVerify { mockArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `navigate to article details`() {
        // given
        val articleItem = FakeArticlesData.articleItems[0]
        val navDirections = ArticleListFragmentDirections.actionListToDetailFragment(articleItem)

        // when
        viewModel.navigateToArticleDetail(articleItem)

        // then
        coVerify { mockNavManager.navigate(navDirections) }
    }

    @Test
    fun `verify state when GetMostPopularArticlesUseCase returns empty list`() {
        // given
        coEvery { mockArticlesUseCase.invoke(any()) } returns Result.Success(emptyList())

        // when
        viewModel.loadData()

        // then
        viewModel.viewStateFlow.value shouldBeEqualTo ArticleListViewModel.ViewState(
            isLoading = false,
            isError = false,
            articles = listOf()
        )
    }

    @Test
    fun `verify state when GetMostPopularArticlesUseCase returns non-empty list`() {
        // given
        val articles = FakeArticlesData.articles
        val articleItems = FakeArticlesData.articleItems
        articles.forEachIndexed { index, article ->
            every { mockArticleItemMapper.mapToPresentation(article) } returns articleItems[index]
        }
        coEvery { mockArticlesUseCase.invoke(any()) } returns Result.Success(articles)

        // when
        viewModel.loadData()

        // then
        viewModel.viewStateFlow.value shouldBeEqualTo ArticleListViewModel.ViewState(
            isLoading = false,
            isError = false,
            articles = articleItems
        )
    }

    @Test
    fun `verify state when SearchArticlesUseCase returns empty list`() {
        // given
        coEvery { mockSearchArticlesUseCase.invoke(any()) } returns Result.Success(emptyList())

        // when
        viewModel.searchArticles(query = "%%")

        // then
        viewModel.viewStateFlow.value shouldBeEqualTo ArticleListViewModel.ViewState(
            isLoading = false,
            isError = false,
            articles = listOf()
        )
    }

    @Test
    fun `verify state when SearchArticlesUseCase returns non-empty list`() {
        // given
        val articles = FakeArticlesData.articles
        val articleItems = FakeArticlesData.articleItems
        articles.forEachIndexed { index, article ->
            every { mockArticleItemMapper.mapToPresentation(article) } returns articleItems[index]
        }
        coEvery { mockSearchArticlesUseCase.invoke(any()) } returns Result.Success(articles)

        // when
        viewModel.searchArticles(query = "%%")

        // then
        viewModel.viewStateFlow.value shouldBeEqualTo ArticleListViewModel.ViewState(
            isLoading = false,
            isError = false,
            articles = articleItems
        )
    }
}