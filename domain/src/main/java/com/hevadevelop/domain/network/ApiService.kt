package com.hevadevelop.domain.network

import com.hevadevelop.domain.model.NewsResponse
import com.hevadevelop.domain.model.NewsSourcesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {


    @GET("everything")
    suspend fun newsArticles(
        @Header("Authorization") authorization: String,
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Header("Authorization") authorization: String,
        @Query("q") q: String,
        @Query("searchIn") searchIn: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): NewsResponse

    @GET("top-headlines/sources")
    suspend fun getNewsCategories(
        @Header("Authorization") authorization: String,
        @Query("category") category: String,
    ): Response<NewsSourcesResponse>

}