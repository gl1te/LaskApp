import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.Article
import com.newsapp.lask.presentation.common.ArticlesList
import com.newsapp.lask.presentation.common.EmptyScreen
import com.newsapp.lask.presentation.common.SearchBar
import com.newsapp.lask.presentation.utils.Dimens.MediumPadding1
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit,
    navigateToSearch: () -> Unit,
    justForYouNewsFirst: LazyPagingItems<Article>,
    justForYouNewsSecond: LazyPagingItems<Article>,
    justForYouNewsThird: LazyPagingItems<Article>,
) {

    val news = listOf(justForYouNewsFirst, justForYouNewsSecond, justForYouNewsThird)
    val errorState =
        news.firstOrNull { it.loadState.refresh is LoadState.Error }?.loadState?.refresh as? LoadState.Error

    var date by remember { mutableStateOf(getCurrentDate()) }
    val handler = remember { Handler(Looper.getMainLooper()) }

    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83d\uDFE5 ") { it.title }
            } else {
                ""
            }
        }
    }

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
                text = "",
                readOnly = true,
                onValueChange = {},
                onClick = {
                    navigateToSearch()
                },
                onSearch = {}
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyScreen(error = errorState)
            }
        }
    } else {
        LazyColumn {
            item {
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
                    text = "",
                    readOnly = true,
                    onValueChange = {},
                    onClick = {
                        navigateToSearch()
                    },
                    onSearch = {}
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = titles, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = MediumPadding1)
                        .basicMarquee(),
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.placeholder)
                )
            }

            item {
                ArticlesList(
                    articles,
                    onClick = {
                        navigateToDetails(it)
                    }
                )
            }
            item {
                Text(
                    text = "Just For You",
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 25.dp, top = 15.dp)
                )
            }
            item {
                news.map { articles ->
                    ArticlesList(
                        articles,
                        onClick = {
                            navigateToDetails(it)
                        }
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
