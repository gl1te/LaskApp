package com.newsapp.lask.domain.repository

import androidx.paging.PagingData
import com.newsapp.lask.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(sources: List<String>, query: String): Flow<PagingData<Article>>

    fun searchNews(searchQuery: String): Flow<PagingData<Article>>

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun selectArticles(): Flow<List<Article>>

    suspend fun getArticleByName(url: String): Article?
}