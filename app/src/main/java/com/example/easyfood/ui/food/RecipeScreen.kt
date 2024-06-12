package com.example.easyfood.ui.food
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.easyfood.R
import com.example.easyfood.components.ImageItem
import com.example.easyfood.ui.food.partials.CreatorLazyrow
import com.example.easyfood.ui.food.partials.HomeCategory
import com.example.easyfood.ui.food.partials.ImagePopularMeals
import com.example.easyfood.ui.food.partials.MealItemSearched
import com.example.easyfood.ui.food.partials.RecentRecipe
import com.example.easyfood.ui.food.store.RecipeViewModel
import com.example.easyfood.ui.food.store.SharedViewModel
data class Creator(val imageResId: Int, val name: String)
val creators = listOf(
    Creator(R.drawable.avatar, "Creator 1"),
    Creator(R.drawable.creater, "Creator 2"),
    Creator(R.drawable.avatar2__1_, "Creator 2"),
    Creator(R.drawable.creater, "Creator 2"),
    Creator(R.drawable.avatar2__2_, "Creator 2"),
    Creator(R.drawable.creater, "Creator 2"),
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val popularItemsState by viewModel.popularItems.collectAsState()
    val categoriesState by viewModel.categories.collectAsState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchedMealsState by viewModel.searchedMeals.collectAsState()
    val errorState by viewModel.error.collectAsState()

    // State for the selected category
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val mealsByCategoryState by viewModel.categoryItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Find best recipes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                        Text(
                            text = "for cooking",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        viewModel.searchMeal(query.text)
                    },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text("Search recipes...")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(4.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(50.dp),
                )
            }

            //start of search
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (errorState != null) {
                item {
                    Text(text = "Error: ${errorState}")
                }
            } else {
                items(searchedMealsState ?: emptyList()) { meal ->
                    MealItemSearched(meal = meal, navController = navController)
                }
            }

            // a column with "Trending now" and "See all"
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = " Trending now \uD83D\uDD25",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate("category_detail/${"Seafood"}")
                            }
                        ) {
                            Text(
                                text = "See all",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Forward Icon",
                                tint = Color.Red,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Over Popular Meals LazyRow
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(popularItemsState ?: emptyList()) { categoryMeal ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            ) {
                                ImagePopularMeals(
                                    imageUrl = categoryMeal.strMealThumb,
                                    mealName = categoryMeal.strMeal,
                                    personImageUrl = ""
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Popular Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // LazyRow for popular categories
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    items(categoriesState ?: emptyList()) { category ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = if (selectedCategory == category.strCategory) Color.Red else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selectedCategory = category.strCategory
                                    viewModel.fetchCategoryItems(category.strCategory)
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category.strCategory,
                                color = if (selectedCategory == category.strCategory) Color.White else Color.Red,
                                fontSize = 16.sp,
                                fontWeight = if (selectedCategory == category.strCategory) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Display meals for the selected category in a single LazyRow
            selectedCategory?.let { category ->
                item {
                    HomeCategory(categoryName = category, navController = navController)
                }
            }

            //
            // a column with "Recent recipe" and "See all"
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = " Recent Recipe ",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate("category_detail/${"Seafood"}")
                            }
                        ) {
                            Text(
                                text = "See all",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Forward Icon",
                                tint = Color.Red,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Over Popular Meals LazyRow
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        items(popularItemsState ?: emptyList()) { categoryMeal ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            ) {
                                RecentRecipe(
                                    imageUrl = categoryMeal.strMealThumb,
                                    mealName = categoryMeal.strMeal,
                                    navController= navController
                                )
                            }
                        }
                    }

                }
            }
            //

            //
            // a column with "over popular creators" and "See all"
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = " Popular Creators ",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate("make")
                            }
                        ) {
                            Text(
                                text = "See all",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Red
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Forward Icon",
                                tint = Color.Red,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    CreatorLazyrow(creators = creators, navController=navController)
                }
            }
        }
    }
}

