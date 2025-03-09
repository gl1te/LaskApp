package com.newsapp.lask.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.utils.Dimens.ArticleCardSize
import com.newsapp.lask.presentation.utils.Dimens.ArticleCardSizeHeight
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding2
import com.newsapp.lask.presentation.utils.Dimens.SmallIconSize
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
) {

    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(start = 25.dp, top = 20.dp)
            .width(ArticleCardSize)
            .height(ArticleCardSizeHeight)
            .clickable {
                onClick()
            }
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.title,
                fontSize = 20.sp,
                color = colorResource(id = R.color.text_title),
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Text(
                    text = formatDateTime(article.publishedAt),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.text_title),
                    lineHeight = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
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