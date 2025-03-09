package com.example.newsapp.presentation.news_navigator

import HomeScreen
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.example.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.bookmark.BookmarkScreen
import com.newsapp.lask.presentation.bookmark.BookmarkViewModel
import com.newsapp.lask.presentation.details.DetailsEvent
import com.newsapp.lask.presentation.details.DetailsScreen
import com.newsapp.lask.presentation.details.DetailsViewModel
import com.newsapp.lask.presentation.home.HomeViewModel
import com.newsapp.lask.presentation.navgraph.Route
import com.newsapp.lask.presentation.search.SearchScreen
import com.newsapp.lask.presentation.search.SearchViewModel

@Preview
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(
                icon = Icons.Default.Home,
                text = R.string.home
            ),
            BottomNavigationItem(
                icon = Icons.Default.Search,
                text = R.string.search
            ),
            BottomNavigationItem(
                icon = Icons.Default.Favorite,
                text = R.string.Bookmark
            )
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }
                    })
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        val topPadding = it.calculateTopPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(top = topPadding, bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                val justForYouNewsFirst = viewModel.justForYouNewsFirst.collectAsLazyPagingItems()
                val justForYouNewsSecond = viewModel.justForYouNewsSecond.collectAsLazyPagingItems()
                val justForYouNewsThird = viewModel.justForYouNewsThird.collectAsLazyPagingItems()
                HomeScreen(
                    justForYouNewsFirst = justForYouNewsFirst,
                    justForYouNewsSecond = justForYouNewsSecond,
                    justForYouNewsThird = justForYouNewsThird,
                    articles = articles,
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    },
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    })
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val justForYouNewsFirst = viewModel.justForYouNewsFirst.collectAsLazyPagingItems()
                val justForYouNewsSecond = viewModel.justForYouNewsSecond.collectAsLazyPagingItems()
                val state = viewModel.state.value
                SearchScreen(
                    justForYouNewsFirst = justForYouNewsFirst,
                    justForYouNewsSecond = justForYouNewsSecond,
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(
                            navController = navController,
                            article = it
                        )
                    })
            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val context = LocalContext.current
                val isBookmarked =
                    viewModel.isBookmarked

                val article =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")

                LaunchedEffect(article) {
                    if (article != null) {
                        viewModel.initArticle(article)
                    }
                }

                viewModel.sideEffect?.let { resId ->
                    Toast.makeText(context, stringResource(resId), Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                article?.let { article ->
                    DetailsScreen(
                        article = article,
                        onEvent = viewModel::onEvent,
                        navigateUp = {
                            navController.navigateUp()
                        },
                        isBookmarked = isBookmarked
                    )
                }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
        }
    }

}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}


private fun navigateToDetails(
    navController: NavController,
    article: Article,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}
