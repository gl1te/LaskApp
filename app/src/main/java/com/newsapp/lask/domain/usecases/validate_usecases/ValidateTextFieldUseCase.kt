package com.newsapp.lask.domain.usecases.validate_usecases

import com.example.practice.domain.usecases.validate_usecases.ValidationResult
import com.newsapp.lask.R
import javax.inject.Inject

class ValidateTextFieldUseCase @Inject constructor() {

    operator fun invoke(text: String): ValidationResult {
        if (text.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.validate_textfield
            )
        }
        return ValidationResult(successful = true)
    }
}