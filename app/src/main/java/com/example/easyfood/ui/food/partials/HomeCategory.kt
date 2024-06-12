package com.example.easyfood.ui.food.partials


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.easyfood.ui.food.store.RecipeViewModel
import com.example.easyfood.utils.truncateText

@Composable
fun HomeCategory(
    categoryName: String,
    navController: NavController,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    LaunchedEffect(categoryName) {
        recipeViewModel.fetchCategoryItems(categoryName)
    }

    val categoryItems by recipeViewModel.categoryItems.collectAsState(emptyList())

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),


       // horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categoryItems) { meal ->
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .width(140.dp)
                    .padding(8.dp)

                    .clickable {
                        navController.navigate("saved")
                    }
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(170.dp)
                        .padding(top = 40.dp)

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .fillMaxSize()
                            .background(Color.LightGray),

                    )

                    {
                        Spacer(modifier = Modifier.height(60.dp))
                        Text(
                            text = truncateText(meal.strMeal ?: "", 12),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {

                            Column() {
                            Text(
                                text = "Time",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 8.dp)
                            )

                            Text(
                                text = "10 Mins",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 8.dp)
                            )
                            }
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Save",
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp).padding(8.dp)
                            )
                        }
                    }
                }
                SubcomposeAsyncImage(
                    model = meal.strMealThumb,
                    contentDescription = null,
                    loading = {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(60.dp))
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    },
                    modifier = Modifier
                        .offset(y = (-80).dp)  // Adjust this value based on the image height
                        .size(90.dp)
                        .clip(RoundedCornerShape(60.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
