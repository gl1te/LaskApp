package com.newsapp.lask

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.lask.domain.usecases.app_entry_usecases.AppEntryUseCases
import com.newsapp.lask.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val sharedPreferences: SharedPreferences // Добавляем SharedPreferences для проверки JWT
) : ViewModel() {
    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartFromHomeScreen ->
            // Проверяем, пройден ли онбординг
            if (shouldStartFromHomeScreen) {
                // Проверяем, авторизован ли пользователь
                val isLoggedIn = sharedPreferences.getString("jwt", null) != null
                startDestination = if (isLoggedIn) {
                    Route.NewsNavigation.route
                } else {
                    Route.AuthNavigation.route
                }
            } else {
                // Онбординг еще не пройден
                startDestination = Route.AppStartNavigation.route
            }
            delay(300) // Задержка для сплеш-скрина
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}