package com.newsapp.lask.domain.usecases

import com.newsapp.lask.domain.usecases.app_entry_usecases.AuthenticateUseCase
import com.newsapp.lask.domain.usecases.auth_usecases.SignInUseCase
import com.newsapp.lask.domain.usecases.auth_usecases.SignUpUseCase

data class AuthUseCases (
    val signInUseCase: SignInUseCase,
    val signUpUseCase: SignUpUseCase,
    val authenticateUseCase: AuthenticateUseCase
)