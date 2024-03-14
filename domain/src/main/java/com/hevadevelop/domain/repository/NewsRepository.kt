package com.hevadevelop.domain.repository

import com.hevadevelop.domain.BuildConfig.api_key
import com.hevadevelop.domain.model.NewsResponse
import com.hevadevelop.domain.model.NewsSourcesResponse
import com.hevadevelop.domain.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: ApiService) {

    private val _articleSource = MutableStateFlow(value = NewsSourcesResponse("", listOf()))

    val articleSource: StateFlow<NewsSourcesResponse>
        get() = _articleSource

    suspend fun getSourcesNews() {
        val result =  api.getNewsCategories(authorization = api_key, category = "general")
        if (result.isSuccessful && result.body() != null) {
            _articleSource.emit(result.body()!!)
        }
    }




}