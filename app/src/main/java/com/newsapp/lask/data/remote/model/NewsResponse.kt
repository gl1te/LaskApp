package com.newsapp.lask.data.remote.model

import com.newsapp.lask.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)