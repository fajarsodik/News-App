package com.hevadevelop.domain.repository

import androidx.compose.runtime.mutableStateOf
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hevadevelop.domain.BuildConfig.api_key
import com.hevadevelop.domain.common.Resource
import com.hevadevelop.domain.model.NewsSourcesResponse
import com.hevadevelop.domain.network.ApiService
import com.hevadevelop.domain.repository.paging.NewsArticlesPagingSource
import com.hevadevelop.domain.repository.paging.SearchNewsPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: ApiService) {

    private val _articleSource = MutableStateFlow(value = NewsSourcesResponse("", listOf()))

    val articleSource: StateFlow<NewsSourcesResponse>
        get() = _articleSource

    suspend fun getSourcesNews(category: String): Resource<NewsSourcesResponse> {
        val result = try {
            api.getNewsCategories(authorization = api_key, category = category)
        } catch (e: Exception) {
            return Resource.Error(e.localizedMessage ?: "couldn't reach server")
        }
        _articleSource.emit(result)
        return Resource.Success(result)
    }

    fun searchNews(q: String) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = {
            SearchNewsPagingSource(api, q)
        }
    ).flow

    fun articlesSourceCategory(source: String) = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 2),
        pagingSourceFactory = {
            NewsArticlesPagingSource(api, source)
        }
    ).flow

}