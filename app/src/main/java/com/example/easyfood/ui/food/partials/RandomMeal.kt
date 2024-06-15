
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage

import com.example.easyfood.model.Meal
import com.example.easyfood.model.MealList
import com.example.easyfood.ui.food.store.SharedViewModel
import androidx.compose.ui.res.stringResource
import com.example.easyfood.R

@Composable
fun DisplayMeals(meals: MealList, navController: NavController, sharedViewModel: SharedViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(start = 20.dp)
    ) {
        meals.meals?.forEach { meal ->
            MealItem(meal, navController, sharedViewModel)
        }
    }
}

@Composable
fun MealItem(meal: Meal, navController: NavController, sharedViewModel: SharedViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .width(270.dp)
                .clickable {
                    sharedViewModel.selectMeal(meal)
                    navController.navigate("detail")
                },
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                meal.strMealThumb?.let { imageUrl ->
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = null,
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
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            }
        }
    }
}


