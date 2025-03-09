package com.newsapp.lask.presentation.signup

import com.example.practice.presentation.login.LoginEvent

sealed class SignUpEvent {
    data class LoginChanged(val login: String) : SignUpEvent()
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class ConfirmPasswordChanged(val password: String) : SignUpEvent()
    object Submit : SignUpEvent()
}