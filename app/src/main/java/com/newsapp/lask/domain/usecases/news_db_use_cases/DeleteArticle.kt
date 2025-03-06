package com.newsapp.lask.domain.usecases.news_db_use_cases

import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteArticle @Inject constructor(
    private val newsRepository: NewsRepository,
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article)
    }

}