package com.inhouse.cleannytimesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inhouse.cleannytimesapp.BuildConfig
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.usecase.articles.GetMostPopularArticlesUseCase
import com.inhouse.cleannytimesapp.model.ArticleItem
import com.inhouse.cleannytimesapp.model.ArticleItemMapper
import com.inhouse.cleannytimesapp.navigation.NavManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val mostPopularArticlesUseCase: GetMostPopularArticlesUseCase,
    private val articleItemMapper: ArticleItemMapper,
    private val navManager: NavManager
) :
    ViewModel() {
    private val _networkErrorState = MutableLiveData<Boolean?>()
    val networkErrorState: LiveData<Boolean?> = _networkErrorState

    private val _articleList = MutableLiveData<List<ArticleItem>?>()
    val articleList: LiveData<List<ArticleItem>?> = _articleList

    init {
        getArticleList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            mostPopularArticlesUseCase.invoke(
                GetMostPopularArticlesUseCase.Params(
                    7,
                    BuildConfig.API_KEY
                )
            ).let {
                _networkErrorState.postValue(
                    when (it) {
                        is Result.Error -> true
                        else -> {
                            _articleList.value = (it as Result.Success).data.map { article ->
                                articleItemMapper.mapToPresentation(article)
                            }
                            false
                        }
                    }
                )
            }
        }
    }

    fun refreshForInitialDataFetch() {
        getArticleList()
    }

    fun navigateToArticleDetail(article: ArticleItem) {
        val navDirections = ArticleListFragmentDirections.actionListToDetailFragment(article)
        navManager.navigate(navDirections)
    }
}