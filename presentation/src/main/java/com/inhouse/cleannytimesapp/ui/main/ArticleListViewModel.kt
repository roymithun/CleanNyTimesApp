package com.inhouse.cleannytimesapp.ui.main

import androidx.lifecycle.viewModelScope
import com.inhouse.cleannytimesapp.BuildConfig
import com.inhouse.cleannytimesapp.base.viewmodel.BaseAction
import com.inhouse.cleannytimesapp.base.viewmodel.BaseViewModel
import com.inhouse.cleannytimesapp.base.viewmodel.BaseViewState
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.usecase.articles.GetMostPopularArticlesUseCase
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
    private val articleItemMapper: ArticleItemMapper,
    private val navManager: NavManager
) :
    BaseViewModel<ArticleListViewModel.ViewState, ArticleListViewModel.Action>(ViewState()) {

    init {
        onLoadData()
    }

    override fun onLoadData() {
        // reset state
        sendAction(Action.ArticleListLoadingIdle)
        getArticleList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            mostPopularArticlesUseCase.invoke(
                GetMostPopularArticlesUseCase.Params(
                    7,
                    BuildConfig.API_KEY
                )
            ).let { result ->
                val action = when (result) {
                    is Result.Success -> {
                        Timber.d("result.data = ${result.data}")
                        val articles: List<ArticleItem> =
                            result.data.map { articleItemMapper.mapToPresentation(it) }

                        if (result.data.isEmpty()) {
                            Action.ArticleListLoadingFailure
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
        else -> state.copy(
            isLoading = true,
            isError = false,
            articles = listOf()
        )
    }
}