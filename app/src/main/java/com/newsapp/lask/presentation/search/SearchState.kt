package com.newsapp.lask.presentation.search

import androidx.paging.PagingData
import com.newsapp.lask.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null,
)