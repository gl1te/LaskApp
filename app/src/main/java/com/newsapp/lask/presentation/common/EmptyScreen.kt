package com.newsapp.lask.presentation.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.newsapp.lask.R
import java.net.ConnectException
import java.net.SocketTimeoutException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
) {
    var message by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }

    var icon by remember {
        mutableStateOf(R.drawable.ic_network_error)

    }

    if (error == null) {
        message = R.string.nothing_bookmark
        icon = R.drawable.ic_search_document
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = ""
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(alpha = alphaAnimation, message = message, icon = icon)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmptyContent(alpha: Float, message: Int, icon: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = colorResource(id = R.color.body),
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha)
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .alpha(alpha),
            text = stringResource(message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
        )
    }
}

fun parseErrorMessage(error: LoadState.Error?): Int {
    return when (error?.error) {
        is SocketTimeoutException -> R.string.server_exception

        is ConnectException -> R.string.internet_exception

        else -> R.string.unknown_exception
    }
}
