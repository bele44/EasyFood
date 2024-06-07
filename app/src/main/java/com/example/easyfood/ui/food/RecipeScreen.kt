package com.example.easyfood.ui.food

import DisplayMeals
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


