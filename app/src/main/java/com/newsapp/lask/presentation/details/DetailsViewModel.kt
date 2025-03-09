package com.newsapp.lask.presentation.details

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<Int?>(null)
        private set

    var isBookmarked by mutableStateOf(false)
        private set

    fun initArticle(article: Article) {
        viewModelScope.launch {
            val existingArticle = newsUseCases.getArticleByName(article.url)
            isBookmarked = existingArticle != null
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = newsUseCases.getArticleByName(event.article.url)
                    if (article == null) {
                        upsertArticle(event.article)
                        isBookmarked = true
                    } else {
                        deleteArticle(event.article)
                        isBookmarked = false
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = R.string.article_deleted
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        sideEffect = R.string.article_saved
    }
}