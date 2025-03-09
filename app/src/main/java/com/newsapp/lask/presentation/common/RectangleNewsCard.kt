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
import com.newsapp.lask.presentation.utils.Dimens.ArticleCardSizeSmall
import com.newsapp.lask.presentation.utils.Dimens.ArticleCardSizeWidthSmall
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding2
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding3
import com.newsapp.lask.presentation.utils.Dimens.SmallIconSize

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RectangleNewsCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
) {

    val context = LocalContext.current
    Row(
        modifier = modifier
            .padding(start = 25.dp, top = 15.dp, end = 25.dp)
            .clickable {
                onClick()
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(ArticleCardSizeSmall )
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(article.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding3)
                .height(ArticleCardSizeSmall)
        ) {
            Text(
                text = article.title,
                fontSize = 16.sp,
                color = colorResource(id = R.color.text_title),
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
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
        }
    }
}
