package com.newsapp.lask.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.newsapp.lask.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    val news = newsUseCases.getNewsUseCase(
        sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
    ).cachedIn(viewModelScope)

    val justForYouNewsFirst = newsUseCases.getNewsUseCase(
        sources = listOf("techcrunch"),
    ).cachedIn(viewModelScope)

    val justForYouNewsSecond = newsUseCases.getNewsUseCase(
        sources = listOf("the-wall-street-journal"),
    ).cachedIn(viewModelScope)

    val justForYouNewsThird = newsUseCases.getNewsUseCase(
        sources = listOf("the-verge"),
    ).cachedIn(viewModelScope)

}