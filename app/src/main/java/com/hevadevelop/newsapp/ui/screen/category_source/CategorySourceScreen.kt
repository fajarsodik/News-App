package com.hevadevelop.newsapp.ui.screen.category_source

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySourceScreen(
    navController: NavHostController,
    categoriesName: String,
    categoriesSlug: String,
) {
    val categorySourceViewModel = hiltViewModel<CategorySourceViewModel>()
    val data = categorySourceViewModel.articles.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
//        categorySourceViewModel.isLoading.value = true
        categorySourceViewModel.getSourceByCategory(categoriesSlug)
//        categorySourceViewModel.isLoading.value = false
        Timber.d("disini coba jalan")
    }
    Timber.d("datanya : ${data.value}")
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "$categoriesName News") }, navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            })
        },
    ) {
        if (categorySourceViewModel.isLoading.value) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        } else {
            Column(
                modifier = Modifier.padding(it)
            ) {
                if (data.value.articles.isNotEmpty()) {
                    LazyColumn {
                        items(
                            count = data.value.articles.size,
                            key = { index ->
                                data.value.articles[index].id
                            }
                        ) { key ->
                            ElevatedCard(
                                onClick = {
                                    navController.navigate("article_source_screen/${data.value.articles[key].name}/${data.value.articles[key].id}")
                                }, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 6.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background
                                )
                            ) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(22.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${data.value.articles[key].name}",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "${data.value.articles[key].category}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "${data.value.articles[key].description}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Oops, something went wrong",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = {
                        scope.launch {
                            categorySourceViewModel.getSourceByCategory(
                                categoriesSlug
                            )
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}