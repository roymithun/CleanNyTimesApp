package com.inhouse.cleannytimesapp.ui.main

import androidx.lifecycle.viewModelScope
import com.inhouse.cleannytimesapp.BuildConfig
import com.inhouse.cleannytimesapp.base.viewmodel.BaseAction
import com.inhouse.cleannytimesapp.base.viewmodel.BaseViewModel
import com.inhouse.cleannytimesapp.base.viewmodel.BaseViewState
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.usecase.articles.GetMostPopularArticlesUseCase
import com.inhouse.cleannytimesapp.domain.usecase.articles.SearchArticlesUseCase
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.model.ArticleItemMapper
import com.inhouse.cleannytimesapp.navigation.NavManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val mostPopularArticlesUseCase: GetMostPopularArticlesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val articleItemMapper: ArticleItemMapper,
    private val navManager: NavManager
) :
    BaseViewModel<ArticleListViewModel.ViewState, ArticleListViewModel.Action>(ViewState()) {

    var lastQueryText: String? = null

    init {
        onLoadData()
    }

    override fun onLoadData() {
        // reset state
        sendAction(Action.ArticleListLoadingIdle)
        lastQueryText?.let {
            searchArticles(lastQueryText)
        } ?: run { getArticleList() }
    }

    fun searchArticles(query: String?) {
        lastQueryText = query
        viewModelScope.launch {
            searchArticlesUseCase.invoke(
                SearchArticlesUseCase.Params(query ?: "%%")
            ).let { processArticleListResult(it) }
        }
    }

    private fun processArticleListResult(result: Result<List<Article>>) {
        val action = when (result) {
            is Result.Success -> {
                Timber.d("result.data = ${result.data}")
                val articles: List<ArticleItem> =
                    result.data.map { articleItemMapper.mapToPresentation(it) }

                if (result.data.isEmpty()) {
                    Action.ArticleListEmptyResult
                } else {
                    Action.ArticleListLoadingSuccess(articles)
                }
            }
            else -> {
                Action.ArticleListLoadingFailure
            }
        }
        sendAction(action)
    }

    private fun getArticleList() {
        viewModelScope.launch {
            mostPopularArticlesUseCase.invoke(
                GetMostPopularArticlesUseCase.Params(
                    7,
                    BuildConfig.API_KEY
                )
            ).let { processArticleListResult(it) }
        }
    }

    fun navigateToArticleDetail(article: ArticleItem) {
        val navDirections = ArticleListFragmentDirections.actionListToDetailFragment(article)
        navManager.navigate(navDirections)
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val articles: List<ArticleItem> = listOf()
    ) : BaseViewState

    sealed interface Action : BaseAction {
        class ArticleListLoadingSuccess(val articles: List<ArticleItem>) : Action
        object ArticleListEmptyResult : Action
        object ArticleListLoadingFailure : Action
        object ArticleListLoadingIdle : Action
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.ArticleListLoadingSuccess -> state.copy(
            isLoading = false,
            isError = false,
            articles = viewAction.articles
        )
        is Action.ArticleListLoadingFailure -> state.copy(
            isLoading = false,
            isError = true,
            articles = listOf()
        )
        is Action.ArticleListEmptyResult -> state.copy(
            isLoading = false,
            isError = false,
            articles = listOf()
        )
        else -> state.copy(
            isLoading = true,
            isError = false,
            articles = listOf()
        )
    }
}