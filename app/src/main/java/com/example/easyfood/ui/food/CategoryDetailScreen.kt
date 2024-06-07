import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.easyfood.ui.food.store.RecipeViewModel
import com.example.easyfood.ui.food.store.SharedViewModel
import com.example.easyfood.utils.truncateText

@Composable
fun CategoryDetailScreen(
    categoryName: String,
    navController: NavController,
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        recipeViewModel.fetchCategoryItems(categoryName)
    }

    val categoryItems by recipeViewModel.categoryItems.collectAsState(emptyList())

    // UI for Category Detail Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = " $categoryName (${categoryItems.size.toString()})",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.clip(RoundedCornerShape(10.dp)).align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
                .background(Color.Blue),
            color = Color.White
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            //verticalArrangement = Arrangement.spacedBy(16.dp),
            //horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categoryItems) { meal ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            //sharedViewModel.selectMeal(meal)

                            navController.navigate("popular/${meal.idMeal}")
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = meal.strMealThumb ,
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
                                    Text("Error")
                                }
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .width(120.dp)
                                .height(100.dp)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = truncateText(meal.strMeal ?: "", 10),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}
