package com.hevadevelop.domain.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hevadevelop.domain.BuildConfig.api_key
import com.hevadevelop.domain.model.NewsResponse
import com.hevadevelop.domain.network.ApiService
import retrofit2.HttpException
import java.io.IOException

class SearchNewsPagingSource(private val apiService: ApiService, private val q: String) :
    PagingSource<Int, NewsResponse.Articles>() {
    override fun getRefreshKey(state: PagingState<Int, NewsResponse.Articles>): Int? =
        state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsResponse.Articles> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchNews(
                authorization = api_key,
                q = q,
                searchIn = "title,content",
                page = page,
                pageSize = 10
            )

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.articles.isEmpty()) null else page.plus(1)
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}