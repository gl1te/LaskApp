package com.newsapp.lask.domain.usecases.news_db_use_cases

import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SelectArticles @Inject constructor(
    private val newsRepository: NewsRepository,
) {

    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.selectArticles()
    }

}