package com.hevadevelop.newsapp.ui.screen.search

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.hevadevelop.domain.model.NewsResponse
import com.hevadevelop.newsapp.ui.component.SearchBarWidget
import timber.log.Timber
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val news: LazyPagingItems<NewsResponse.Articles> =
        viewModel.newsSearchResult.collectAsLazyPagingItems()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(8.dp)
        ){
            SearchBarWidget(
                query = query,
                onQueryChanged = {
                    viewModel.clearSearchQuery(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Search"
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (query.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(news.itemCount) {
//                        val instant = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            Instant.parse(news[it]!!.publishedAt)
//                        } else {
//                            // Parse the input string
//
//                            // Parse the input string
//                            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//                            val date: Date
//                            date = try {
//                                inputFormat.parse(news[it]!!.publishedAt)
//                            } catch (e: ParseException) {
//                                e.printStackTrace()
//                                return
//                            }
//
//                            // Format the date as "yyyy-MM-dd"
//
//                            // Format the date as "yyyy-MM-dd"
//                            val outputFormat = SimpleDateFormat("yyyy-MM-dd")
//                            val formattedDate: String = outputFormat.format(date)

//                        }
                        // Parse the input string

                        // Parse the input string
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        val date: Date = try {
                            inputFormat.parse(news[it]!!.publishedAt)
                        } catch (e: ParseException) {
                            e.printStackTrace()
                            return@items
                        }

                        // Format the date as "yyyy-MM-dd"

                        // Format the date as "yyyy-MM-dd"
                        val outputFormat = SimpleDateFormat("yyyy-MM-dd")
                        val formattedDate: String = outputFormat.format(date)
//                        val offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneOffset.UTC)
//                        val formattedDate =
//                            offsetDateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))
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
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(100.dp)
                                        .weight(1f)
                                )
                            }
                        }
                    }
                }

                news.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = news.loadState.refresh as LoadState.Error
                            Timber.e("errornya di awal: ${error.endOfPaginationReached} ${error.error} $error")
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

                        loadState.append is LoadState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }

                        loadState.append is LoadState.Error -> {
                            val error = news.loadState.append as LoadState.Error
//                            Timber.e("errornya di akhir: ${error.endOfPaginationReached} ${error.error} $error")
                            if (error.error.localizedMessage!! == "HTTP 426") {
//                                Timber.e("error from server $error")
                            } else if (error.endOfPaginationReached) {
//                                Timber.e("Finish pagination")
                            } else {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "pagination",
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