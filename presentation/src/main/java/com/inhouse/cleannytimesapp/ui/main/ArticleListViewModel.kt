package com.inhouse.cleannytimesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inhouse.cleannytimesapp.model.ArticleUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor() :
    ViewModel() {
    private val _networkErrorState = MutableLiveData<Boolean?>()
    val networkErrorState: LiveData<Boolean?> = _networkErrorState

    private val _navigateToArticleDetail = MutableLiveData<ArticleUiModel?>()
    val navigateToArticleDetail: LiveData<ArticleUiModel?> = _navigateToArticleDetail
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