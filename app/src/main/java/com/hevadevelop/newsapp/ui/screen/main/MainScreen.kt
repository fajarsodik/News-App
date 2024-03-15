package com.hevadevelop.newsapp.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hevadevelop.domain.model.Categories
import com.hevadevelop.newsapp.R
import com.hevadevelop.newsapp.ui.component.WaveLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
//    val mainViewModel = hiltViewModel<MainViewModel>()
//    val data = mainViewModel.articles.collectAsState()
//    Timber.d("testgan $data ${data.value} ${data.value.articles.size}")
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            WaveLayout(navController)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "News Categories", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(
                        count = allCategories.size,
                        key = { index ->
                            allCategories[index].slug
                        }
                    ) { key ->
                        ElevatedCard(
                            onClick = {

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
                                modifier = Modifier.fillMaxSize().padding(6.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(painter = painterResource(id = allCategories[key].image), contentDescription = allCategories[key].name, modifier = Modifier.height(100.dp))
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = "${allCategories[key].name}", style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

val allCategories = listOf(
    Categories(
        name = "Business",
        slug = "business",
        image = R.drawable.strategy_development
    ),
    Categories(
        name = "Sports",
        slug = "sports",
        image = R.drawable.badminton
    ),
    Categories(
        name = "Technology",
        slug = "technology",
        image = R.drawable.cloud_computing
    ),
    Categories(
        name = "Entertainment",
        slug = "entertainment",
        image = R.drawable.video_player
    ),
    Categories(
        name = "General",
        slug = "general",
        image = R.drawable.newspaper
    ),
    Categories(
        name = "Health",
        slug = "health",
        image = R.drawable.medical_check
    ),
    Categories(
        name = "Science",
        slug = "science",
        image = R.drawable.data_science
    )

)