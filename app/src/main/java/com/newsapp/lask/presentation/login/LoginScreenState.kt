package com.example.practice.presentation.login

import androidx.annotation.StringRes

data class LoginScreenState(
    var login: String = "",
    @StringRes var loginError: Int? = null,
    var password: String = "",
    @StringRes var passwordError: Int? = null,
    var onSuccess: Boolean = false,
)
