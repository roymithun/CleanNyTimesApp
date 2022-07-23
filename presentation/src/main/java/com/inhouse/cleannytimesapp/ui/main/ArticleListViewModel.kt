package com.inhouse.cleannytimesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inhouse.cleannytimesapp.BuildConfig
import com.inhouse.cleannytimesapp.domain.Result
import com.inhouse.cleannytimesapp.domain.model.Article
import com.inhouse.cleannytimesapp.domain.usecase.articles.GetMostPopularArticlesUseCase
import com.inhouse.cleannytimesapp.model.ArticleItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val mostPopularArticlesUseCase: GetMostPopularArticlesUseCase
) :
    ViewModel() {
    private val _networkErrorState = MutableLiveData<Boolean?>()
    val networkErrorState: LiveData<Boolean?> = _networkErrorState

    private val _navigateToArticleDetail = MutableLiveData<ArticleItem?>()
    val navigateToArticleDetail: LiveData<ArticleItem?> = _navigateToArticleDetail

    init {
        getArticleList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            /*val getArticlesResult: Result<List<Article>> = */mostPopularArticlesUseCase.invoke(
                GetMostPopularArticlesUseCase.Params(
                    7,
                    BuildConfig.API_KEY
                )
            ).let {
                println("gibow: ${it}")
                if (it is Result.Success) {
                    println("gibow__xx: ${it.data}")
                }
            }
        }
    }
/*
    fun articleList(filter: String = "%%") = articleRepository.observableArticleList(filter)

    init {
        getArticleList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            val fetchArticles: Resource<List<ArticleUiModel>> = articleRepository.fetchArticles(7)
            _networkErrorState.postValue(
                when (fetchArticles.status) {
                    Status.ERROR -> {
                        articleList().value.isNullOrEmpty()
                    }
                    else -> false
                }
            )
        }
    }

    fun refreshForInitialDataFetch() {
        getArticleList()
    }

    fun resetNetworkErrorStatus() {
        _networkErrorState.value = null
    }

    fun showArticleDetail(article: ArticleUiModel) {
        _navigateToArticleDetail.value = article
    }

    fun doneNavigationToDetail() {
        _navigateToArticleDetail.value = null
    }*/
}