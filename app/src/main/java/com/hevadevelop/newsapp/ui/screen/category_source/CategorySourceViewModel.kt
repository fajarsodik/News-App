package com.hevadevelop.newsapp.ui.screen.category_source

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hevadevelop.domain.common.Resource
import com.hevadevelop.domain.model.NewsSourcesResponse
import com.hevadevelop.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySourceViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    var isLoading = mutableStateOf(false)
    val articles : StateFlow<NewsSourcesResponse>
        get() = newsRepository.articleSource

    suspend fun getSourceByCategory(category: String) {
        isLoading.value = true
        viewModelScope.launch {
            newsRepository.getSourcesNews(category = category)
            isLoading.value = false
        }
    }

}