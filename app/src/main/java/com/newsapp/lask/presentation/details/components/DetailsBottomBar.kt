package com.example.newsapp.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.newsapp.lask.R

@Composable
fun DetailsBottomBar(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean,
    onBrowsingClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
) {

    val switchIcon by remember { mutableStateOf(false) }

    BottomAppBar(
        modifier = modifier
            .height(56.dp),
        containerColor = Color.Transparent,
        contentColor = colorResource(id = R.color.body)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_arrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(onClick = {
                onBookmarkClick()
                switchIcon != switchIcon
            }) {
                Icon(
                    painter = painterResource(
                        if (isBookmarked) R.drawable.baseline_bookmark_24 else R.drawable.ic_bookmark
                    ),
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = { onBrowsingClick() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_network),
                    contentDescription = "Browse",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}