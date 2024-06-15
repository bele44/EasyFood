package com.example.easyfood.ui.food

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource


import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.easyfood.R
import com.example.easyfood.ui.food.store.OfflineViewModel
import com.example.easyfood.ui.food.store.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    offlineViewModel: OfflineViewModel
) {
    val selectedMeal by sharedViewModel.selectedMeal.collectAsState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = selectedMeal?.strMeal ?: stringResource(R.string.detail_screen)) },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        SubcomposeAsyncImage(
                            model = selectedMeal?.strMealThumb,
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
                            ) {
                                Row {
                                    Icon(Icons.Default.List, contentDescription = stringResource(R.string.category_icon))
                                    Text(
                                        text = stringResource(R.string.category) + ": ${selectedMeal?.strCategory}",
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                                Row {
                                    Icon(Icons.Default.LocationOn, contentDescription = stringResource(R.string.location_icon))
                                    Text(
                                        text = stringResource(R.string.area) + ": ${selectedMeal?.strArea}",
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            FloatingActionButton(
                                onClick = {
                                    selectedMeal?.let { meal ->
                                        offlineViewModel.upsertMeal(meal)
                                        showToast(context, "Meal Saved")
                                    } ?: showToast(context, "No meal selected")
                                },
                                modifier = Modifier.size(48.dp),
                                containerColor = Color.Red
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = stringResource(R.string.favorite),
                                    tint = Color.White
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
                selectedMeal?.strYoutube?.let { youtubeUrl ->
                    FloatingActionButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp)
                            .size(48.dp),
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
        }
    )
}


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
