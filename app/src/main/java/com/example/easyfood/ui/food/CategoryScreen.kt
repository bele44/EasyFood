package com.example.easyfood.ui.food

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.easyfood.model.Category
import com.example.easyfood.ui.food.store.RecipeViewModel
import androidx.compose.ui.res.stringResource
import com.example.easyfood.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val categoriesState = viewModel.categories.collectAsState()

    // Check if the categories state is not null and not empty
    val categories = categoriesState.value ?: emptyList()
    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = { Text(text = stringResource(R.string.meal_category)) },
                            modifier = Modifier.height(70.dp)
                        )


    if (categories.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                items(categories) { category ->
                    CategoryItem(category = category, navController = navController)
                }
            }
        } else {
            // Show a loading indicator or empty state if needed
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.loading_categories))
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("category_detail/${category.strCategory}")
            },
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            SubcomposeAsyncImage(
                model = category.strCategoryThumb,
                contentDescription = category.strCategory,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.error))
                    }
                },
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = category.strCategory,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}
