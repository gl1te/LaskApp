package com.example.practice.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.AuthResult
import com.newsapp.lask.domain.repository.AuthRepository
import com.newsapp.lask.domain.usecases.ValidateDataUseCases
import com.newsapp.lask.domain.usecases.app_login_usecases.SaveAppLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateDataUseCases: ValidateDataUseCases,
    private val saveAppLoginUseCase: SaveAppLoginUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(LoginScreenState())
    val state: State<LoginScreenState> = _state

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginChanged -> {
                _state.value = _state.value.copy(
                    login = event.login,
                    loginError = null,
                    passwordError = null
                )
            }
            is LoginEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is LoginEvent.Submit -> {
                viewModelScope.launch {
                    submitData()
                }

            }

            else -> {}
        }
    }

    private fun submitData() {
        viewModelScope.launch {
            val state = _state.value
            if (state.login.isBlank()) {
                _state.value = _state.value.copy(loginError = R.string.validate_textfield)
                return@launch
            }
            if (state.password.isBlank()) {
                _state.value = _state.value.copy(passwordError = R.string.validate_textfield)
                return@launch
            }
            val result = authRepository.signIn(
                username = _state.value.login,
                password = _state.value.password
            )

            when (result) {
                is AuthResult.Authorized -> {
                    // Навигация уже обрабатывается в UI через LaunchedEffect
                }

                is AuthResult.Unauthorized -> {
                    _state.value = _state.value.copy(
                        loginError = R.string.error_invalid_credentials,
                        passwordError = R.string.error_invalid_credentials
                    )
                }

                is AuthResult.UnknownError -> {
                    _state.value = _state.value.copy(
                        loginError = R.string.error_invalid_credentials,
                        passwordError = R.string.error_invalid_credentials
                    )
                }
            }

            resultChannel.send(result)
            saveAppLoginEntry()
        }
    }


    private fun saveAppLoginEntry() {
        viewModelScope.launch {
            saveAppLoginUseCase()
        }
    }
}
