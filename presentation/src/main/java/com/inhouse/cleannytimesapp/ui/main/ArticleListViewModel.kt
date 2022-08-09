package com.inhouse.cleannytimesapp.ui.main

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.inhouse.cleannytimesapp.BuildConfig
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.usecase.articles.GetMostPopularArticlesUseCase
import com.inhouse.cleannytimesapp.domain.usecase.articles.SearchArticlesUseCase
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.model.ArticleItemMapper
import com.inhouse.cleannytimesapp.navigation.NavManager
import com.inhouse.cleannytimesapp.util.Constants.PERIOD
import dagger.Binds
import dagger.Module
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap
import kotlinx.coroutines.launch
import timber.log.Timber

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ExampleViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    fun articleListViewModelFactory(factory: ArticleListViewModel.Factory): AssistedViewModelFactory<*, *>
}

class ArticleListViewModel @AssistedInject constructor(
    private val mostPopularArticlesUseCase: GetMostPopularArticlesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val articleItemMapper: ArticleItemMapper,
    private val navManager: NavManager,
    @Assisted initialState: ViewState
) : MavericksViewModel<ArticleListViewModel.ViewState>(initialState) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ArticleListViewModel, ViewState> {
        override fun create(state: ViewState): ArticleListViewModel
    }

    companion object :
        MavericksViewModelFactory<ArticleListViewModel, ViewState> by hiltMavericksViewModelFactory()

    var lastQueryText: String? = null

    init {
        loadData()
    }

    fun loadData() {
        onLoadData()
    }

    /*override fun onLoadData() {*/
    private fun onLoadData() {
        onReduceState(Action.ArticleListLoadingIdle)
        lastQueryText?.let {
            searchArticles(lastQueryText)
        } ?: run {
            getArticleList()
        }
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
        onReduceState(action)
//        sendAction(action)
    }

    private fun getArticleList() {
        viewModelScope.launch {
            mostPopularArticlesUseCase.invoke(
                GetMostPopularArticlesUseCase.Params(
                    PERIOD,
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
    ) : MavericksState

    sealed interface Action {
        class ArticleListLoadingSuccess(val articles: List<ArticleItem>) : Action
        object ArticleListEmptyResult : Action
        object ArticleListLoadingFailure : Action
        object ArticleListLoadingIdle : Action
    }

    private fun onReduceState(viewAction: Action) = setState {
        when (viewAction) {
            is Action.ArticleListLoadingSuccess -> copy(
                isLoading = false,
                isError = false,
                articles = viewAction.articles
            )
            is Action.ArticleListLoadingFailure -> copy(
                isLoading = false,
                isError = true,
                articles = listOf()
            )
            is Action.ArticleListEmptyResult -> copy(
                isLoading = false,
                isError = false,
                articles = listOf()
            )
            else -> copy(
                isLoading = true,
                isError = false,
                articles = listOf()
            )
        }
    }
}
