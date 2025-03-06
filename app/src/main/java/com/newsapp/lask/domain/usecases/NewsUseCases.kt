package com.newsapp.lask.domain.usecases

import com.newsapp.lask.domain.usecases.news_db_use_cases.DeleteArticle
import com.newsapp.lask.domain.usecases.news_db_use_cases.GetArticleByName
import com.newsapp.lask.domain.usecases.news_db_use_cases.SelectArticles
import com.newsapp.lask.domain.usecases.news_db_use_cases.UpsertArticle
import com.newsapp.lask.domain.usecases.news_usecases.GetNewsUseCase
import com.newsapp.lask.domain.usecases.news_usecases.SearchNewsUseCase

data class NewsUseCases(
    val getNewsUseCase: GetNewsUseCase,
    val searchNewsUseCase: SearchNewsUseCase,
    val selectArticles: SelectArticles,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val getArticleByName: GetArticleByName
)
