package com.hevadevelop.newsapp.ui.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hevadevelop.newsapp.ui.screen.article_source.ArticleSourceScreen
import com.hevadevelop.newsapp.ui.screen.category_source.CategorySourceScreen
import com.hevadevelop.newsapp.ui.screen.detail_news.DetailNewsScreen
import com.hevadevelop.newsapp.ui.screen.main.MainScreen
import com.hevadevelop.newsapp.ui.screen.search.SearchScreen

@Composable
fun NewsNavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(navController)
        }
        composable(
            route = "news_categories_screen/{categories_name}/{categories_slug}",
            arguments = listOf(navArgument("categories_name") {
                type = NavType.StringType
            }, navArgument("categories_slug") {
                type = NavType.StringType
            })
        ) {
            val name = it.arguments?.getString("categories_name") ?: ""
            val slug = it.arguments?.getString("categories_slug") ?: ""
//            NewsCategoriesScreen(name, slug, navController)
            CategorySourceScreen(
                navController = navController,
                categoriesName = name,
                categoriesSlug = slug
            )
        }
        composable(
            route = "detail_news_screen/{url_news}",
            arguments = listOf(navArgument("url_news") {
                type = NavType.StringType
            })
        ) {
            val url_news = it.arguments?.getString("url_news") ?: ""
            DetailNewsScreen(url_news, navController)
        }
        composable(route = "search_news") {
            SearchScreen(navController)
        }
        composable(
            route = "article_source_screen/{source_name}/{source}",
            arguments = listOf(navArgument("source_name") {
                type = NavType.StringType
            }, navArgument("source") {
                type = NavType.StringType
            })
        ) {
            val source_name = it.arguments?.getString("source_name") ?: ""
            val source = it.arguments?.getString("source") ?: ""
            ArticleSourceScreen(navController = navController, source = source, source_name = source_name)
        }
    }
}