package com.newsapp.lask.domain.usecases

import com.newsapp.lask.domain.usecases.app_login_usecases.ReadAppLoginUseCase
import com.newsapp.lask.domain.usecases.app_login_usecases.SaveAppLoginUseCase

data class AppLoginUseCases(
    val saveAppLoginUseCase: SaveAppLoginUseCase,
    val readAppLoginUseCase: ReadAppLoginUseCase
)
