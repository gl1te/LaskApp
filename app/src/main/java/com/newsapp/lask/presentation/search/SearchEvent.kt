package com.newsapp.lask.presentation.search

sealed class SearchEvent {

    data class UpdateSearchQuery(val searchQuery: String): SearchEvent()
    data class UpdateCategory(val category: String): SearchEvent()

    object SearchNews: SearchEvent()
    object SearchNewsByCategory: SearchEvent()

}