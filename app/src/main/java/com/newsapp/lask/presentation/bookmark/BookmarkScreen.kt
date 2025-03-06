package com.newsapp.lask.presentation.bookmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.common.ArticlesList
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding4

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(R.color.text_title),
            modifier = Modifier.padding(top = ExtraSmallPadding4, start = 25.dp)
        )
        val articles = state.articles
        ArticlesList(articles = articles, onClick = {
            navigateToDetails(it)
        })
    }
}