package com.newsapp.lask.domain.usecases.news_usecases

import androidx.paging.PagingData
import com.newsapp.lask.data.remote.model.NewsResponse
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
) {

    operator fun invoke(sources: List<String>): Flow<PagingData<Article>> {
        return newsRepository.getNews(sources = sources)
    }

}