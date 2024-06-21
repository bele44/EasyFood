package com.example.easyfood.ui.food

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.easyfood.R
import com.example.easyfood.ui.food.partials.SavedRecipes
import com.example.easyfood.ui.food.store.RecipeViewModel

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.foundation.Image
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.easyfood.ui.food.partials.MakingProcess
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun SavedScreen(viewModel: RecipeViewModel = hiltViewModel(), navController: NavController) {
    val popularItemsState by viewModel.popularItems.collectAsState()
    val tabTitles = listOf(
        stringResource(id = R.string.tab_recipes),
        stringResource(id = R.string.tab_video)
    )

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.saved_recipes),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 1.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    Box(
                        Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(4.dp)
                            .background(Color.Red)
                    )
                },
                divider = {}
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (pagerState.currentPage == index) Color.Red else Color.Black
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                count = tabTitles.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {
                        // Recipes content
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = stringResource(R.string.how_to_make_french_toast),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(8.dp)
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .padding(0.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(1.dp)
                            ) {
                                item {
                                    MakingProcess(navController = navController)
                                }
                                item {
                                    Column {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = stringResource(R.string.ingredients),
                                                fontSize = 22.sp,
                                                color = Color.Black,
                                                modifier = Modifier.padding(start = 4.dp)
                                            )

                                            Text(
                                                text = stringResource(R.string.items_count, 5),
                                                color = Color.Black
                                            )
                                        }
                                        IngredientCardOne(
                                            imageResId = R.drawable.toast,
                                            ingredientName = stringResource(R.string.bread),
                                            quantity = stringResource(R.string.grams, 200)
                                        )
                                        IngredientCardOne(
                                            imageResId = R.drawable.eggs,
                                            ingredientName = stringResource(R.string.eggs),
                                            quantity = stringResource(R.string.grams, 200)
                                        )
                                        IngredientCardOne(
                                            imageResId = R.drawable.toast,
                                            ingredientName = stringResource(R.string.milk),
                                            quantity = stringResource(R.string.grams, 200)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        // Video content
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            items(popularItemsState ?: emptyList()) { categoryMeal ->
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxSize()
                                ) {
                                    SavedRecipes(
                                        imageUrl = categoryMeal.strMealThumb,
                                        mealName = categoryMeal.strMeal,
                                        personImageUrl = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientCardOne(imageResId: Int, ingredientName: String, quantity: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = ingredientName,
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Text(
                text = quantity,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}



