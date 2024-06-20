package com.example.easyfood.ui.navigation

import CategoryDetailScreen
import StartScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import com.example.easyfood.ui.food.DetailScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easyfood.ui.food.CategoryScreen
import com.example.easyfood.ui.food.FavoriteDetail
import com.example.easyfood.ui.food.FavouriteScreen
import com.example.easyfood.ui.food.MakingFood
import com.example.easyfood.ui.food.NotificationsScreen
import com.example.easyfood.ui.food.PopularDetailScreen
import com.example.easyfood.ui.food.ProfileScreen
import com.example.easyfood.ui.food.RecipeCreation
import com.example.easyfood.ui.food.RecipeScreen
import com.example.easyfood.ui.food.SavedScreen
import com.example.easyfood.ui.food.SearchScreen
import com.example.easyfood.ui.food.store.SharedViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FoodNavApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentRoute by navController.currentBackStackEntryAsState()
            if (currentRoute?.destination?.route !in listOf(
                    Screen.Detail.route,
                    Screen.PopularDetail.route,
                    Screen.CategoryDetailScreen.route,
                    Screen.Start.route,
                    Screen.Make.route,
                    Screen.Saved.route,
                    Screen.Create.route,
                    Screen.FavoriteDetail.route
                )) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        val sharedViewModel: SharedViewModel = hiltViewModel()
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Start.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Start.route) {
                    StartScreen(navController = navController)
                }
                composable(Screen.Make.route) {
                    MakingFood(navController = navController)
                }
                composable(Screen.Recipes.route) {
                    RecipeScreen(navController = navController, sharedViewModel = sharedViewModel)
                }
                composable(Screen.Favorites.route) {
                    FavouriteScreen(navController = navController)
                }
                composable(Screen.Category.route) {
                    CategoryScreen(navController = navController)
                }
                composable(Screen.Detail.route) { backStackEntry ->
                    val mealId = backStackEntry.arguments?.getString("idMeal")
                    DetailScreen(sharedViewModel = sharedViewModel, offlineViewModel = hiltViewModel())
                }
                composable(Screen.PopularDetail.route, arguments = listOf(navArgument("idMeal") { type = NavType.StringType })) { backStackEntry ->
                    val idMeal = backStackEntry.arguments?.getString("idMeal")
                    PopularDetailScreen(idMeal, offlineViewModel = hiltViewModel(), navController = navController)
                }
                composable(Screen.CategoryDetailScreen.route, arguments = listOf(navArgument("categoryName") { type = NavType.StringType })) { backStackEntry ->
                    val categoryName = backStackEntry.arguments?.getString("categoryName")
                    CategoryDetailScreen(
                        categoryName = categoryName ?: "",
                        sharedViewModel = hiltViewModel(),
                        navController = navController,
                    )
                }
                composable(Screen.Search.route) {
                    SearchScreen(navController = navController)
                }
                composable(Screen.Saved.route) {
                    SavedScreen(navController = navController)
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(navController = navController)
                }
                composable(Screen.Create.route) {
                    RecipeCreation(navController = navController)
                }
                composable(Screen.Notification.route) {
                    NotificationsScreen(navController = navController)
                }
                composable(Screen.FavoriteDetail.route, arguments = listOf(navArgument("idMeal") { type = NavType.StringType })) { backStackEntry ->
                    val idMeal = backStackEntry.arguments?.getString("idMeal")
                    FavoriteDetail(idMeal, offlineViewModel = hiltViewModel(), navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Recipes, Screen.Favorites, Screen.Category, Screen.Notification, Screen.Profile)
    NavigationBar {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to avoid building up a large stack of destinations
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
