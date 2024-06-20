package com.example.easyfood.ui.food

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.easyfood.R
import com.example.easyfood.components.ImageItem
import com.example.easyfood.ui.food.partials.*
import com.example.easyfood.ui.food.store.RecipeViewModel
import com.example.easyfood.ui.food.store.SharedViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.easyfood.ui.theme.poppinsFontFamily

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
    val seafoodCategory = stringResource(R.string.seafood_category)

    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(categoriesState) {
        if (categoriesState?.isNotEmpty() == true && selectedCategory == null) {
            val initialCategory = categoriesState?.first()?.strCategory
            selectedCategory = initialCategory
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Column(modifier = Modifier.padding(0.dp)) {
                    Text(
                        text = stringResource(R.string.find_best_recipes),
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = stringResource(R.string.for_cooking),
                        style = MaterialTheme.typography.headlineLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            //modifier = Modifier.height(97.dp)
        )


        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(0.dp)


        )
        {
            val maxWidth = maxWidth

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    var isFocused by remember { mutableStateOf(false) }

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
                                    contentDescription = stringResource(R.string.search_recipes),
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text(
                                    text = stringResource(R.string.search_recipes),
                                    color = if (isFocused) MaterialTheme.colorScheme.onPrimary else Color.Gray,
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(4.dp)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                            }
                            .border(
                                width = 2.dp,
                                color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50.dp)
                            )
                            .padding(2.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            disabledBorderColor = Color.Transparent,
                            errorBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                    )
                }

                // Start of search
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

                //  "Trending now" and "See all"
                item {
                    Column(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.trending_now),
                                    style = MaterialTheme.typography.headlineSmall,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    navController.navigate("category_detail/$seafoodCategory")
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.see_all),
                                    color = Color.Red,
                                    style = MaterialTheme.typography.headlineSmall,
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
                        text = stringResource(R.string.popular_categories),
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.headlineSmall,
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
                                    .padding(start = 8.dp)
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
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedCategory == category.strCategory) FontWeight.Bold else FontWeight.Normal,
                                    fontFamily = poppinsFontFamily
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

                // Column with "Recent recipe" and "See all"
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.recent_recipe),
                                    style = MaterialTheme.typography.headlineSmall,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    navController.navigate("category_detail/$seafoodCategory")
                                }
                            ) {
                                Text(
                                    text = stringResource(R.string.see_all),
                                    style = MaterialTheme.typography.headlineSmall,
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
                                        navController = navController
                                    )
                                }
                            }
                        }
                    }
                }

                // over popular creators
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.popular_creators),
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
                                    text = stringResource(R.string.see_all),
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.headlineSmall,
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
                        CreatorLazyrow(creators = creators, navController = navController)
                    }
                }
            }
        }
    }
}

