package com.newsapp.lask.domain.usecases

import com.newsapp.lask.domain.usecases.validate_usecases.ValidateConfirmPasswordUseCase
import com.newsapp.lask.domain.usecases.validate_usecases.ValidateEmailUseCase
import com.newsapp.lask.domain.usecases.validate_usecases.ValidateLoginUseCase
import com.newsapp.lask.domain.usecases.validate_usecases.ValidatePasswordUseCase
import com.newsapp.lask.domain.usecases.validate_usecases.ValidateTextFieldUseCase

data class ValidateDataUseCases(
    val validateEmailUseCase: ValidateEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateTextFieldUseCase: ValidateTextFieldUseCase,
    val validateLoginUseCase: ValidateLoginUseCase,
    val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
)