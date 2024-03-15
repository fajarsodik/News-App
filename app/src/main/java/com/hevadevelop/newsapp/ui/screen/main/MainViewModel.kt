package com.hevadevelop.newsapp.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hevadevelop.domain.model.NewsSourcesResponse
import com.hevadevelop.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    val articles : StateFlow<NewsSourcesResponse>
        get() = newsRepository.articleSource

    init {
        viewModelScope.launch {
            newsRepository.getSourcesNews()
        }
    }


}