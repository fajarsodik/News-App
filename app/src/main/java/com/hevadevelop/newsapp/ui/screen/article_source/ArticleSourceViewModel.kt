package com.hevadevelop.newsapp.ui.screen.article_source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hevadevelop.domain.model.NewsResponse
import com.hevadevelop.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ArticleSourceViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    fun getNews(source: String): Flow<PagingData<NewsResponse.Articles>> = repository.articlesSourceCategory(source)
        .cachedIn(viewModelScope)
}