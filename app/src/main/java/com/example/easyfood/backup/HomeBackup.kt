/*
package com.example.easyfood.backup

import DisplayMeals
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.easyfood.ui.food.store.RecipeViewModel
import com.example.easyfood.ui.food.store.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val mealsState = viewModel.meals.collectAsState()
    val errorState = viewModel.error.collectAsState()
    val popularItemsState = viewModel.popularItems.collectAsState()
    val categoriesState = viewModel.categories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchRandomMeal()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home",color = Color.White) },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("search")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "What would you like to eat?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 1.dp)
                    .align(Alignment.CenterHorizontally)
            )
            if (errorState.value != null) {
                Text(text = "Error Loading The Image")
            } else {
                mealsState.value?.let { meals ->
                    DisplayMeals(meals, navController, sharedViewModel)
                }
            }

            Text(
                text = "Over Popular Meals",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            popularItemsState.value?.let { items ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(items) { categoryMeal ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .width(90.dp)
                                .height(90.dp)
                                .clickable {
                                    sharedViewModel.selectPopularMeal(categoryMeal)
                                    navController.navigate("popular/${categoryMeal.idMeal}")
                                },
                        ) {
                            ImageItem(imageUrl = categoryMeal.strMealThumb)
                        }
                    }
                }
            }

            Text(
                text = "Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )

            categoriesState.value?.let { categories ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val itemsPerRow = 3
                    categories.chunked(itemsPerRow).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            rowItems.forEach { category ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .width(100.dp)
                                        .height(70.dp)
                                        .background(Color.LightGray)
                                        .padding(top = 8.dp)
                                        .clickable {
                                            // Handle category click
                                            navController.navigate("category_detail/${category.strCategory}")
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    ImageItem(imageUrl = category.strCategoryThumb)
                                }
                            }
                            // Add empty boxes if the row is not completely filled
                            repeat(itemsPerRow - rowItems.size) {
                                Spacer(modifier = Modifier.width(100.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(imageUrl: String?) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = null,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        error = {
            Text("Error")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("Image Load Error")
            }
        },
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
    )
}


*//*

package com.example.easyfood.ui.navigation

import CategoryDetailScreen
import StartScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import com.example.easyfood.ui.food.DetailScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easyfood.ui.food.*
import com.example.easyfood.ui.food.store.SharedViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodNavApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("EasyFood") },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            val sharedViewModel: SharedViewModel = hiltViewModel()
            NavHost(
                navController = navController,
                startDestination = Screen.Start.route,
                modifier = Modifier.padding(innerPadding)
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
                composable(Screen.Favorites.route) { FavouriteScreen(navController = navController) }
                composable(Screen.Category.route) { CategoryScreen(navController = navController) }
                composable(Screen.Detail.route) { backStackEntry ->
                    val mealId = backStackEntry.arguments?.getString("idMeal")
                    DetailScreen(sharedViewModel = sharedViewModel, offlineViewModel = hiltViewModel())
                }
                composable(Screen.PopularDetail.route, arguments = listOf(navArgument("idMeal") { type = NavType.StringType })) { backStackEntry ->
                    val idMeal = backStackEntry.arguments?.getString("idMeal")
                    PopularDetailScreen(idMeal, offlineViewModel = hiltViewModel())
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
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState) {
    val items = listOf(Screen.Recipes, Screen.Favorites, Screen.Notification, Screen.Saved, Screen.Profile)
    val coroutineScope = rememberCoroutineScope()
    Column {
        items.forEach { screen ->
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(screen.icon, contentDescription = screen.label)
                Spacer(modifier = Modifier.width(8.dp))
                Text(screen.label)
            }
        }}}*/
