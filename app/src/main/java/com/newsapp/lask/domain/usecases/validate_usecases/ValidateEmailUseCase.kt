package com.newsapp.lask.domain.usecases.validate_usecases

import android.util.Patterns
import com.example.practice.domain.usecases.validate_usecases.ValidationResult
import com.newsapp.lask.R
import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.email_error_message
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.email_error_message_valid
            )
        }
        return ValidationResult(successful = true)
    }

}