package com.newsapp.lask.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.utils.Dimens.MediumPadding1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit,
) {
    if (articles.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            count = articles.size
        ) {
            val article = articles[it]
            RectangleNewsCard(article = article, onClick = { onClick(article) })
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticlesList(
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
) {
    val loadState = articles.loadState
    val isLoading = loadState.refresh is LoadState.Loading

    if (isLoading) {
        ShimmerEffect()
    } else if (articles.itemCount > 0) {
        LazyRow {
            items(
                count = articles.itemCount
            ) {
                articles[it]?.let { article ->
                    NewsCard(article = article, onClick = { onClick(article) })
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RectangleArticlesList(
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
) {
    val loadState = articles.loadState
    val isLoading = loadState.refresh is LoadState.Loading

    if (isLoading) {
        ShimmerEffectRectangle()
    } else if (articles.itemCount > 0) {
        LazyColumn {
            items(
                count = articles.itemCount
            ) {
                articles[it]?.let { article ->
                    RectangleNewsCard(article = article, onClick = { onClick(article) })
                }
            }
        }
    }
}


@Composable
fun ShimmerEffect() {
    Row(horizontalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}

@Composable
fun ShimmerEffectRectangle() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffectRectangle(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}