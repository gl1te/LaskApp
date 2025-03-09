package com.newsapp.lask.presentation.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.news_navigator.NewsNavigator
import com.example.practice.presentation.login.LoginViewModel
import com.newsapp.lask.presentation.login.LoginScreen
import com.newsapp.lask.presentation.onboarding.OnBoardingScreen
import com.newsapp.lask.presentation.onboarding.OnBoardingViewModel
import com.newsapp.lask.presentation.signup.SignUpScreen
import com.newsapp.lask.presentation.signup.SignUpViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        // Онбординг
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent,
                    onComplete = {
                        // После онбординга переходим к аутентификации
                        navController.navigate(Route.AuthNavigation.route) {
                            popUpTo(Route.AppStartNavigation.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Аутентификация (Login и SignUp)
        navigation(
            route = Route.AuthNavigation.route,
            startDestination = Route.LoginScreen.route
        ) {
            composable(route = Route.LoginScreen.route) {
                val viewModel: LoginViewModel = hiltViewModel()
                LoginScreen(
                    viewModel = viewModel,
                    navigateHomeScreen = {
                        // Успешный логин -> Новости
                        navController.navigate(Route.NewsNavigation.route) {
                            popUpTo(Route.AuthNavigation.route) { inclusive = true }
                        }
                    },
                    navigateToSignUp = {
                        // Переход к регистрации
                        navController.navigate(Route.SignUpScreen.route)
                    }
                )
            }
            composable(route = Route.SignUpScreen.route) {
                val viewModel: SignUpViewModel = hiltViewModel()
                SignUpScreen(
                    viewModel = viewModel,
                    navigateToLogin = {
                        // После регистрации -> Логин
                        navController.navigate(Route.LoginScreen.route) {
                            popUpTo(Route.SignUpScreen.route) { inclusive = true }
                        }
                    },
                    navigateHomeScreen = {
                        // Успешная регистрация -> Новости
                        navController.navigate(Route.NewsNavigation.route) {
                            popUpTo(Route.AuthNavigation.route) { inclusive = true }
                        }
                    }
                )
            }
        }

        // Новости
        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {
                NewsNavigator()
            }
        }
    }
}