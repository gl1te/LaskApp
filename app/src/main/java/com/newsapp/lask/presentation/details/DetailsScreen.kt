package com.newsapp.lask.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsapp.presentation.details.components.DetailsBottomBar
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.utils.Dimens.ArticleImageHeight
import com.newsapp.lask.presentation.utils.Dimens.MediumPadding1
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(UnstableApi::class)
@Composable
fun DetailsScreen(
    article: Article,
    isBookmarked: Boolean,
    onEvent: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(start = 25.dp, top = 20.dp, bottom = 56.dp, end = 20.dp)
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = article.description,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.text_title),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = article.content,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.text_title),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(MediumPadding1))

                article.author?.let {
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.text_title),
                        lineHeight = 20.sp
                    )
                }
                Text(
                    text = formatDateTime(article.publishedAt),
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.text_title),
                    lineHeight = 20.sp
                )


            }
        }

        DetailsBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            isBookmarked = isBookmarked, // Передаем состояние из ViewModel
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = {
                onEvent(DetailsEvent.UpsertDeleteArticle(article = article))
            },
            onBackClick = {
                navigateUp()
            },

        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTime(isoString: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val outputFormatter =
            DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm")
        val dateTime = LocalDateTime.parse(isoString, inputFormatter)
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        "Invalid Date"
    }
}