package com.newsapp.lask.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.AuthResult
import com.newsapp.lask.domain.usecases.AuthUseCases
import com.newsapp.lask.domain.usecases.ValidateDataUseCases
import com.newsapp.lask.domain.usecases.app_login_usecases.SaveAppLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val validateDataUseCases: ValidateDataUseCases,
    private val saveAppLoginUseCase: SaveAppLoginUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    emailError = null
                )
            }
            is SignUpEvent.LoginChanged -> {
                _state.value = _state.value.copy(
                    login = event.login,
                    loginError = null
                )
            }
            is SignUpEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = event.password,
                    passwordError = null,
                    passwordConfirmError = null
                )
            }
            is SignUpEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(
                    passwordConfirm = event.password,
                    passwordConfirmError = null
                )
            }

            is SignUpEvent.Submit -> {
                signUp()
            }

        }
    }

    private fun signUp() {

        val loginResult = validateDataUseCases.validateLoginUseCase(_state.value.login)
        val emailResult = validateDataUseCases.validateEmailUseCase(_state.value.email)
        val passwordResult = validateDataUseCases.validatePasswordUseCase(_state.value.password)
        val confirmPassword = validateDataUseCases.validateConfirmPasswordUseCase(
            _state.value.password,
            _state.value.passwordConfirm
        )

        val hasError =
            listOf(emailResult, passwordResult, loginResult, confirmPassword).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                passwordConfirmError = confirmPassword.errorMessage,
                loginError = loginResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            val result = authUseCases.signUpUseCase(
                username = _state.value.login,
                password = _state.value.password,
                email = _state.value.email
            )
            when (result) {
                is AuthResult.Authorized -> {
                    saveAppLoginEntry()
                }
                is AuthResult.Unauthorized -> {
                    _state.value = _state.value.copy(
                        loginError = R.string.error_username_taken
                    )
                }
                is AuthResult.UnknownError -> {
                    _state.value = _state.value.copy(
                        loginError = R.string.error_username_taken
                    )
                }
            }

            Log.e("register", result.toString())
            Log.e("register", authResults.toString())

            resultChannel.send(result)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            val result = authUseCases.authenticateUseCase()
            resultChannel.send(result)
        }
    }

    private fun saveAppLoginEntry() {
        viewModelScope.launch {
            saveAppLoginUseCase()
        }
    }

}