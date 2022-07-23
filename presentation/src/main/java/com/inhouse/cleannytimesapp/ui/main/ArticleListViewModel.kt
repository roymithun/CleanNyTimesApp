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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val mostPopularArticlesUseCase: GetMostPopularArticlesUseCase,
    private val articleItemMapper: ArticleItemMapper
) :
    ViewModel() {
    private val _networkErrorState = MutableLiveData<Boolean?>()
    val networkErrorState: LiveData<Boolean?> = _networkErrorState

    private val _articleList = MutableLiveData<List<ArticleItem>?>()
    val articleList: LiveData<List<ArticleItem>?> = _articleList

    private val _navigateToArticleDetail = MutableLiveData<ArticleItem?>()
    val navigateToArticleDetail: LiveData<ArticleItem?> = _navigateToArticleDetail

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

    fun showArticleDetail(article: ArticleItem) {
        _navigateToArticleDetail.value = article
    }

    fun doneNavigationToDetail() {
        _navigateToArticleDetail.value = null
    }
}