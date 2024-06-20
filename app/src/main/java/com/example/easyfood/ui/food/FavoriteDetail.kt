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

import android.app.AlertDialog
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDetail(idMeal: String?, recipeViewModel: RecipeViewModel = hiltViewModel(), offlineViewModel: OfflineViewModel= hiltViewModel(), navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }  // State for dialog visibility

    LaunchedEffect(idMeal) {
        offlineViewModel.fetchMealDetails(idMeal)
    }

    val selectedMeal by offlineViewModel.offlineMealById.collectAsState(null)

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
                            text = selectedMeal?.strMeal ?: "Detail Screen",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 1.dp)
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
    ) { innerPadding ->
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
                    error = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(stringResource(R.string.error))
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
                    verticalAlignment = Alignment.CenterVertically
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
                            showDialog.value = true  // Show the confirmation dialog
                        },
                        modifier = Modifier.size(40.dp),
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(R.string.favorite),
                            tint = Color.Red
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

    // Confirmation Dialog
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = stringResource(R.string.confirm_delete_title)) },
            text = { Text(text = stringResource(R.string.confirm_delete_message)) },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    selectedMeal?.let { meal ->
                        offlineViewModel.deleteMeal(meal)
                        Toast.makeText(context, "Meal deleted", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } ?: Toast.makeText(context, "No meal selected", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = stringResource(R.string.confirm), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text(text = stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        )
    }
}
