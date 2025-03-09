package com.newsapp.lask.presentation.signup

import androidx.annotation.StringRes

data class SignUpState (
    var email: String = "",
    @StringRes var emailError: Int? = null,
    var password: String = "",
    @StringRes var passwordError: Int? = null,
    var passwordConfirm: String = "",
    @StringRes var passwordConfirmError: Int? = null,
    var login: String = "",
    @StringRes var loginError: Int? = null,
    var onSuccess: Boolean = false
)