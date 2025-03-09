package com.newsapp.lask.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.newsapp.lask.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    val justForYouNewsFirst = newsUseCases.getNewsUseCase(
        sources = listOf("espn"),
        query = "sport",
    ).cachedIn(viewModelScope)

    val justForYouNewsSecond = newsUseCases.getNewsUseCase(
        sources = listOf("bloomberg"),
        query = "airplane",
    ).cachedIn(viewModelScope)

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(
                    searchQuery = event.searchQuery,
                    articlesCategory = null,
                    articles = if (event.searchQuery.isBlank()) null else state.value.articles // Сбрасываем articles, если запрос пустой
                )
            }

            is SearchEvent.SearchNews -> {
                searchNews()
            }

            is SearchEvent.UpdateCategory -> {
                _state.value = state.value.copy(
                    category = event.category,
                    articles = null // Сбрасываем articles при выборе категории
                )
                searchNewsByCategory()
            }

            is SearchEvent.SearchNewsByCategory -> {
                searchNewsByCategory()
            }
        }
    }

    private fun searchNews() {
        if (state.value.searchQuery.isBlank()) {
            return // Ничего не делаем при пустом запросе
        }
        val articles = newsUseCases.searchNewsUseCase(
            searchQuery = state.value.searchQuery,
        ).cachedIn(viewModelScope)
        _state.value = state.value.copy(
            articles = articles,
            articlesCategory = null
        )
    }

    private fun searchNewsByCategory() {
        val articles = newsUseCases.searchNewsUseCase(
            searchQuery = state.value.category,
        ).cachedIn(viewModelScope)
        _state.value = state.value.copy(
            articlesCategory = articles,
            articles = null
        )
    }
}