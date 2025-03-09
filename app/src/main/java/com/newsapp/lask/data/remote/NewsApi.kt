package com.newsapp.lask.data.remote

import com.newsapp.lask.data.remote.model.NewsResponse
import com.newsapp.lask.data.utils.Constants
import com.newsapp.lask.data.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ): NewsResponse

}