package com.example.practice.presentation.login

sealed class LoginEvent {
    data class LoginChanged(val login: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Submit : LoginEvent()
}