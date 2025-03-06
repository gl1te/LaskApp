package com.newsapp.lask.domain.usecases.app_entry_usecases

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntryUseCase,
    val saveAppEntry: SaveAppEntryUseCase
)