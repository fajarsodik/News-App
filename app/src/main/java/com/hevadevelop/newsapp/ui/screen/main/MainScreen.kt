package com.hevadevelop.newsapp.ui.screen.main

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.hevadevelop.domain.model.Categories
import com.hevadevelop.newsapp.R
import com.hevadevelop.newsapp.ui.component.WaveLayout
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Timber.d("testkannnnn")
    val mainViewModel = hiltViewModel<MainViewModel>()
    val data = mainViewModel.articles.collectAsState()
//    Log.d("testong", "hasilnyaa $data ${data.value}}")
    Timber.d("testgan $data ${data.value} ${data.value.articles.size}")
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            WaveLayout()
            Spacer(modifier = Modifier.height(2.dp))
            ElevatedCard(
                onClick = {
//                    navController.navigate("daftar_hotline_screen")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
//                Log.d("data", "datanya ${allCategories}")
                Spacer(modifier = Modifier.height(8.dp))
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    val (textDescription, icon) = createRefs()
                    Text(
                        text = "description",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.constrainAs(textDescription) {
                            start.linkTo(parent.start)
                            width = Dimension.fillToConstraints
                        }
                    )
                    Text(
                        text = "description",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.constrainAs(icon) {
                            end.linkTo(parent.end)
                            width = Dimension.wrapContent
                        }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(
                        data.value.articles.size
                    ) { article ->
                        ElevatedCard(
                            onClick = {

                            }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                            ) {
                                val (textDescription, icon) = createRefs()
//                                Text(
//                                    text = "${data.value.articles[article].id}",
//                                    overflow = TextOverflow.Ellipsis,
//                                    modifier = Modifier.constrainAs(textDescription) {
//                                        start.linkTo(parent.start)
//                                        width = Dimension.fillToConstraints
//                                    }
//                                )
                                Text(
                                    text = "${data.value.articles[article].name}",
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.constrainAs(icon) {
                                        end.linkTo(parent.end)
                                        width = Dimension.wrapContent
                                    }
                                )
                            }
                        }
                    }
                }
            }
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