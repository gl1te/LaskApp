package com.newsapp.lask.domain.usecases.news_usecases

import androidx.paging.PagingData
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {

    operator fun invoke(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.searchNews(searchQuery, sources)
    }

}