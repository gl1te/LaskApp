package com.newsapp.lask.presentation.bookmark

import com.newsapp.lask.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList(),
)