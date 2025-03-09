package com.newsapp.lask.domain.usecases.auth_usecases

import com.newsapp.lask.domain.model.AuthResult
import com.newsapp.lask.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
    ): AuthResult<Unit> {
        return authRepository.signUp(username, email, password)
    }

}