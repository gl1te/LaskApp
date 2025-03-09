package com.newsapp.lask.domain.usecases.app_entry_usecases

import com.newsapp.lask.domain.model.AuthResult
import com.newsapp.lask.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(): AuthResult<Unit> {
        return authRepository.authenticate()
    }

}