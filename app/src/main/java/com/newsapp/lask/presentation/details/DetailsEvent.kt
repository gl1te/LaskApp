package com.newsapp.lask.presentation.details

import com.newsapp.lask.domain.model.Article

sealed class DetailsEvent {

    data class UpsertDeleteArticle(
        val article: Article,
    ) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()

}