package com.example.practice.domain.usecases.validate_usecases

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: Int? = null
)
