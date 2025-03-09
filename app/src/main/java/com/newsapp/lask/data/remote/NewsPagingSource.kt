package com.newsapp.lask.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.newsapp.lask.domain.model.Article
import java.util.Locale
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val systemLanguage = Locale.getDefault().language

        val languageCode = when (systemLanguage) {
            "ru" -> "ru"
            else -> "en"
        }

        val page = params.key ?: 1
        return try {
            val newsResponse =
                newsApi.getNews( page = page, language = languageCode, query = query)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title } // Remove duplicates
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

}
