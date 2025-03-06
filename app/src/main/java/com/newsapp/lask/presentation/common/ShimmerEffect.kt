package com.newsapp.lask.presentation.common

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.newsapp.lask.R
import com.newsapp.lask.presentation.utils.Dimens.ArticleCardSize
import com.newsapp.lask.presentation.utils.Dimens.ArticleCardSizeSmall
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding
import com.newsapp.lask.presentation.utils.Dimens.ExtraSmallPadding3
import com.newsapp.lask.presentation.utils.Dimens.MediumPadding1
import com.newsapp.lask.presentation.utils.Dimens.MediumPadding2

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    background(color = colorResource(R.color.shimmer).copy(alpha = alpha))
}

@Preview
@Composable
fun ArticleCardShimmerEffect(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .padding(start = 25.dp, top = 20.dp)
            .border(1.dp, Color.Transparent, RoundedCornerShape(12.dp))
            .width(ArticleCardSize)

    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth(0.8f)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth(0.5f)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun ArticleCardShimmerEffectRectangle(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = Modifier

    ) {
        Box(
            modifier = modifier
                .padding(top = 20.dp)
                .size(ArticleCardSizeSmall)
                .border(1.dp, Color.Transparent, RoundedCornerShape(12.dp))
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .height(ArticleCardSizeSmall)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .height(35.dp)
                    .padding(end = 25.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(30.dp)
                        .padding(top = 5.dp,end = 25.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
            }
        }
    }

}