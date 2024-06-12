package com.example.easyfood.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Start : Screen("start", "Start", Icons.Default.Home)
    object Recipes : Screen("recipes", "Recipes", Icons.Default.Home)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object Category : Screen("categories", "Category", Icons.Default.List)
    object Detail : Screen("detail", "Detail", Icons.Default.List)
    object PopularDetail : Screen("popular/{idMeal}", "Popular", Icons.Default.List)
    object CategoryDetailScreen : Screen("category_detail/{categoryName}", "CategoriesDetail", Icons.Default.List)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Make : Screen("make", "Make", Icons.Default.Create)
    object Saved : Screen("saved", "saved", Icons.Default.AddCircle)
    object Profile : Screen("profile", "profile", Icons.Default.AccountCircle)
    object Create : Screen("create", "create", Icons.Default.AccountCircle)
    object Notification : Screen("notification", "notific...", Icons.Default.Notifications)
}