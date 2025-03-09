package com.newsapp.lask.presentation.navgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen: Route(route = "onBoardingScreen")
    object SignUpScreen: Route(route = "signup_screen")
    object LoginScreen: Route(route = "login_screen")
    object HomeScreen: Route(route = "homeScreen")
    object SearchScreen: Route(route = "searchScreen")
    object BookmarkScreen: Route(route = "bookmarkScreen")
    object DetailsScreen: Route(route = "detailsScreen")
    object AppStartNavigation: Route(route = "appStartNavigation")
    object NewsNavigation: Route(route = "newsNavigation")
    object AuthNavigation: Route(route = "authNavigation")
    object NewsNavigatorScreen: Route(route = "newsNavigator")
}