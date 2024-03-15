package com.hevadevelop.newsapp.ui.screen.article_source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hevadevelop.newsapp.R
import timber.log.Timber
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleSourceScreen(navController: NavHostController, source: String, source_name: String) {
    val viewmodel = hiltViewModel<ArticleSourceViewModel>()
    val news = viewmodel.getNews(source).collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "$source_name") }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            })
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()

        ) {
            LazyColumn {
                items(count = news.itemCount) {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                    val date: Date = try {
                        inputFormat.parse(news[it]!!.publishedAt)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                        return@items
                    }
                    val outputFormat = SimpleDateFormat("yyyy-MM-dd")
                    val formattedDate: String = outputFormat.format(date)
                    val author = news[it]?.author
                    val title = news[it]?.title
                    val url_news = news[it]?.url
                    ElevatedCard(
                        onClick = {
                            url_news?.let { data ->
                                val newsData =
                                    URLEncoder.encode(data, StandardCharsets.UTF_8.toString())
                                navController.navigate("detail_news_screen/$newsData")
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clip(
                                RoundedCornerShape(8.dp)
                            ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Row(modifier = Modifier.padding(all = 16.dp)) {
                            Column(modifier = Modifier.weight(2f)) {
                                author?.let {
                                    Text(text = author)
                                }
                                title?.let {
                                    Text(
                                        text = it,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(text = formattedDate)
                            }
                            AsyncImage(
                                model = news[it]?.urlToImage,
                                contentDescription = "${news[it]?.description}",
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.photo),
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(100.dp)
                                    .weight(1f),
                                error = painterResource(id = R.drawable.error)
                            )
                        }
                    }
                }

                news.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Column(
                                    modifier = Modifier.fillParentMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = news.loadState.refresh as LoadState.Error
                            Timber.e("errornya di akhir: ${error.endOfPaginationReached} || ${error.error} || $error")
                            item {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Oops, something went wrong",
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.weight(1f),
                                        maxLines = 2
                                    )
                                    OutlinedButton(onClick = { retry() }) {
                                        Text(text = "retry")
                                    }
                                }
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = news.loadState.append as LoadState.Error
                            if (error.error.localizedMessage!! == "HTTP 426") {

                            } else if (error.endOfPaginationReached) {

                            } else {
                                item {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Oops, something went wrong",
                                            color = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.weight(1f),
                                            maxLines = 2
                                        )
                                        OutlinedButton(onClick = { retry() }) {
                                            Text(text = "retry")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}