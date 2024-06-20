package com.example.easyfood.ui.food
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.easyfood.R
import com.example.easyfood.ui.food.store.OfflineViewModel
import com.example.easyfood.ui.food.store.RecipeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularDetailScreen(idMeal: String?, recipeViewModel: RecipeViewModel = hiltViewModel(),offlineViewModel: OfflineViewModel,navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    LaunchedEffect(idMeal) {
        recipeViewModel.fetchMealDetails(idMeal)
    }

    val selectedMeal by recipeViewModel.mealDetails.collectAsState(null)

    Scaffold(

        topBar = {
            TopAppBar(

                title = {
                    Row(modifier = Modifier.padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Icon",
                                tint = Color.Black
                            )
                        }
                        Text(
                            text = selectedMeal?.strMeal?: "Detail Screen",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 1.dp),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                        },
                modifier = Modifier.height(70.dp)
            )
        },
        floatingActionButton = {
            selectedMeal?.strYoutube?.let { youtubeUrl ->
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(48.dp),
                    containerColor = Color.Red
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.youtube),
                        contentDescription = stringResource(R.string.youtube),
                        tint = Color.White
                    )
                }
            }
        }
    )
        { innerPadding ->

                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
                ) {
                    item {
                        SubcomposeAsyncImage(
                            model = selectedMeal?.strMealThumb ?: "",
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

                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row {
                                    Icon(Icons.Default.List, contentDescription = stringResource(R.string.category_icon))
                                    Column {
                                        Text(
                                            text = stringResource(R.string.category) ,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        Text(" ${selectedMeal?.strCategory}")
                                    }

                                }
                                Row{
                                    Icon(Icons.Default.LocationOn, contentDescription = stringResource(R.string.location_icon))
                                    Column {
                                        Text(
                                            text = stringResource(R.string.area) ,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        Text(" ${selectedMeal?.strArea}")
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = {
                                    selectedMeal?.let { meal ->
                                        offlineViewModel.upsertMeal(meal)
                                        shownToast(context, "Meal Saved")
                                    } ?: shownToast(context, "No meal selected")
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.size(35.dp),
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = stringResource(R.string.favorite),
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.instructions),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = selectedMeal?.strInstructions ?: "",
                                modifier = Modifier.padding(bottom = 4.dp),
                                textAlign = TextAlign.Start,
                                maxLines = Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

            }
        }
fun shownToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}