package com.inhouse.cleannytimesapp.ui.main

import app.cash.turbine.test
import com.airbnb.mvrx.test.MvRxTestRule
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
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleListViewModelTest {
    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    @get:Rule
    val mvRxRule = MvRxTestRule()

    @MockK(relaxed = true)
    lateinit var mockArticlesUseCase: GetMostPopularArticlesUseCase

    @MockK(relaxed = true)
    lateinit var mockSearchArticlesUseCase: SearchArticlesUseCase

    @MockK
    lateinit var mockArticleItemMapper: ArticleItemMapper

    @MockK(relaxed = true)
    lateinit var mockNavManager: NavManager

    @MockK(relaxed = true)
    lateinit var viewModel: ArticleListViewModel

    @MockK
    lateinit var viewState: ArticleListViewModel.ViewState

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewState = ArticleListViewModel.ViewState(
            isLoading = true,
            isError = false,
            articles = listOf()
        )
        viewModel = ArticleListViewModel(
            mockArticlesUseCase,
            mockSearchArticlesUseCase,
            mockArticleItemMapper,
            mockNavManager,
            viewState
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
        runBlocking {
            viewModel.stateFlow.test {
                awaitItem().let {
                    it.isLoading shouldBeEqualTo false
                    it.articles shouldBeEqualTo emptyList()
                }
            }
        }
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
        runBlocking {
            viewModel.stateFlow.test {
                expectMostRecentItem().let {
                    it.isLoading shouldBeEqualTo false
                    it.articles shouldBeEqualTo articleItems
                }
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `verify state when SearchArticlesUseCase returns empty list`() {
        // given
        coEvery { mockSearchArticlesUseCase.invoke(any()) } returns Result.Success(emptyList())

        // when
        viewModel.searchArticles(query = "%%")

        // then
        runBlocking {
            viewModel.stateFlow.test {
                expectMostRecentItem().let {
                    it.isLoading shouldBeEqualTo false
                    it.articles shouldBeEqualTo emptyList()
                }
                cancelAndConsumeRemainingEvents()
            }
        }
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
        runBlocking {
            viewModel.stateFlow.test {
                expectMostRecentItem().let {
                    it.isLoading shouldBeEqualTo false
                    it.articles shouldBeEqualTo articleItems
                }
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}