package com.example.easyfood.ui.navigation

import CategoryDetailScreen
import StartScreen
import com.example.easyfood.ui.food.DetailScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easyfood.ui.food.CategoryScreen
import com.example.easyfood.ui.food.FavouriteScreen
import com.example.easyfood.ui.food.PopularDetailScreen
import com.example.easyfood.ui.food.RecipeScreen
import com.example.easyfood.ui.food.SearchScreen
import com.example.easyfood.ui.food.store.SharedViewModel


sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Start : Screen("start", "Start", Icons.Default.Home)
    object Recipes : Screen("recipes", "Recipes", Icons.Default.Home)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object Category : Screen("categories", "Categories", Icons.Default.List)
    object Detail : Screen("detail", "Detail", Icons.Default.List)
    object PopularDetail : Screen("popular/{idMeal}", "Popular", Icons.Default.List)
    object CategoryDetailScreen : Screen("category_detail/{categoryName}", "CategoriesDetail", Icons.Default.List)
    object Search : Screen("search", "Search", Icons.Default.Search)
}
@Composable
fun FoodNavApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {

            val currentRoute by navController.currentBackStackEntryAsState()

            if (currentRoute?.destination?.route != Screen.Detail.route &&
                currentRoute?.destination?.route != Screen.PopularDetail.route
                && currentRoute?.destination?.route != Screen.CategoryDetailScreen.route &&
                    currentRoute?.destination?.route != Screen.Start.route) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        val sharedViewModel: SharedViewModel = hiltViewModel()
        NavHost(
            navController = navController,
            startDestination = Screen.Start.route,
            modifier = Modifier.padding(innerPadding)
        )
        {
            composable(Screen.Start.route) {
            StartScreen(navController = navController)
        }
            composable(Screen.Recipes.route) {
                RecipeScreen(navController = navController, sharedViewModel = sharedViewModel)
            }
            composable(Screen.Favorites.route) { FavouriteScreen(navController=navController) }
            composable(Screen.Category.route) { CategoryScreen(navController=navController) }
            composable(Screen.Detail.route) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString("idMeal")
                DetailScreen(sharedViewModel = sharedViewModel, offlineViewModel = hiltViewModel())
            }
            composable(Screen.PopularDetail.route,arguments = listOf(navArgument("idMeal") { type = NavType.StringType })) { backStackEntry ->
                val idMeal = backStackEntry.arguments?.getString("idMeal")
                PopularDetailScreen(idMeal, offlineViewModel = hiltViewModel())
            }
            composable(Screen.CategoryDetailScreen.route,
                arguments = listOf(navArgument("categoryName") { type = NavType.StringType })) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName")
                CategoryDetailScreen(
                    categoryName = categoryName ?: "",
                    sharedViewModel = hiltViewModel(),
                    navController = navController,
                )
            }
            composable(Screen.Search.route){
                SearchScreen(navController =navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Recipes, Screen.Favorites, Screen.Category)
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
