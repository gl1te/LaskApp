package com.newsapp.lask.domain.usecases.validate_usecases

import com.example.practice.domain.usecases.validate_usecases.ValidationResult
import com.newsapp.lask.R
import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.password_error_message
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.password_error_message_digit
            )
        }
        return ValidationResult(successful = true)
    }

}