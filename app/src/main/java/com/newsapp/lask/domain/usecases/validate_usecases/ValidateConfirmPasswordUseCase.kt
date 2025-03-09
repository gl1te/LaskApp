package com.newsapp.lask.domain.usecases.validate_usecases

import com.example.practice.domain.usecases.validate_usecases.ValidationResult
import com.newsapp.lask.R
import javax.inject.Inject

class ValidateConfirmPasswordUseCase @Inject constructor() {

    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.error_password_mismatch
            )
        }
        return ValidationResult(successful = true)
    }

}