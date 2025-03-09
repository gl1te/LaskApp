package com.newsapp.lask.domain.usecases.validate_usecases

import com.example.practice.domain.usecases.validate_usecases.ValidationResult
import com.newsapp.lask.R
import javax.inject.Inject

class ValidateLoginUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.login_error_message
            )
        }
        return ValidationResult(successful = true)
    }

}