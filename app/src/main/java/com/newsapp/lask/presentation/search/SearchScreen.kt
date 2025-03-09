package com.newsapp.lask.presentation.search

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.common.CategoryList
import com.newsapp.lask.presentation.common.EmptyScreen
import com.newsapp.lask.presentation.common.RectangleArticlesList
import com.newsapp.lask.presentation.common.SearchBar
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding3
import com.newsapp.lask.presentation.utils.Dimens.MediumPadding1
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
    justForYouNewsFirst: LazyPagingItems<Article>,
    justForYouNewsSecond: LazyPagingItems<Article>,
) {
    var date by remember { mutableStateOf(getCurrentDate()) }
    val handler = remember { Handler(Looper.getMainLooper()) }


    LaunchedEffect(Unit) {
        val updateRunnable = object : Runnable {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                date = getCurrentDate()
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(updateRunnable)
    }

    val loadState = justForYouNewsFirst.loadState
    val errorState = loadState.refresh as? LoadState.Error

    if (errorState != null) {
        Column {
            Spacer(modifier = Modifier.height(MediumPadding1))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(30.dp)

                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = MediumPadding1)
                )
            }

            Spacer(modifier = Modifier.height(MediumPadding1))
            SearchBar(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                text = state.searchQuery,
                readOnly = false,
                onValueChange = {
                    event(SearchEvent.UpdateSearchQuery(it))
                },
                onSearch = {
                    event(SearchEvent.SearchNews)
                }
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyScreen(error = errorState)
            }
        }
    } else {
        Column {
            Spacer(modifier = Modifier.height(MediumPadding1))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(30.dp)

                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = MediumPadding1)
                )
            }

            Spacer(modifier = Modifier.height(MediumPadding1))
            SearchBar(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                text = state.searchQuery,
                readOnly = false,
                onValueChange = {
                    event(SearchEvent.UpdateSearchQuery(it))
                },
                onSearch = {
                    event(SearchEvent.SearchNews)
                }
            )
            Spacer(modifier = Modifier.height(ExtraSmallPadding3))
            Box(modifier = Modifier.fillMaxWidth()) {
                CategoryList(
                    onCategoryClick = {
                        event(SearchEvent.UpdateCategory(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(ExtraSmallPadding3))
            when {
                // Показываем статьи по категории, если они есть
                state.articlesCategory != null -> {
                    state.articlesCategory?.let { flow ->
                        val articles = flow.collectAsLazyPagingItems()
                        RectangleArticlesList(
                            articles = articles,
                            onClick = { navigateToDetails(it) }
                        )
                    }
                }
                // Показываем статьи по поиску, если они есть
                state.articles != null -> {
                    state.articles?.let { flow ->
                        val articles = flow.collectAsLazyPagingItems()
                        RectangleArticlesList(
                            articles = articles,
                            onClick = { navigateToDetails(it) }
                        )
                    }
                }
                // Если ничего нет, показываем список по умолчанию
                else -> {
                    RectangleArticlesList(
                        articles = justForYouNewsFirst,
                        onClick = { navigateToDetails(it) }
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun getCurrentDate(): String {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy"))
}

