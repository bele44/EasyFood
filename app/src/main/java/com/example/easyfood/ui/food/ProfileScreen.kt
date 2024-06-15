package com.example.easyfood.ui.food

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.easyfood.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.easyfood.ui.food.partials.ProfileVideo
import com.example.easyfood.ui.food.store.RecipeViewModel

@Composable
fun ProfileScreen(viewModel: RecipeViewModel = hiltViewModel(),navController:NavController){
    val popularItemsState by viewModel.popularItems.collectAsState()
    var selected by remember { mutableStateOf<String?>(null) }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.my_profile),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 1.dp)
                )
                FloatingActionButton(
                    onClick = {
                        navController.navigate("create")
                    },
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp),
                    containerColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = "More",
                        tint = Color.Red,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column() {
                    Image(
                        painter = painterResource(id = R.drawable.avatar2__1_),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.profile_name),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { navController.navigate("create") },
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, Color.Red),
                ) {
                    Text(stringResource(id = R.string.edit_profile), color = Color.Red)
                }
            }
        }

        // Bio
        item {
            Text(
                text = stringResource(id = R.string.hello_cooking),
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.video_creator_bio),
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        // Stats
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            ) {
                StatItem("Recipes", "3")
                StatItem("Videos", "5")
                StatItem("Followers", "13")
                StatItem("Following", "14K")

            }
        }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
                //
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp)
                                .background(
                                    color = if (selected == "video") Color.Red else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selected = "video"

                                })
                        {
                            Text(
                                text = stringResource(id = R.string.tab_video),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selected == "video") Color.White else Color.Red,
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = if (selected == "Recipes") Color.Red else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selected = "Recipes"
                                    navController.navigate("make")
                                }
                        ) {
                            Text(
                                text = stringResource(id = R.string.tab_recipes),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selected == "Recipes") Color.White else Color.Red,
                            )
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
                // Video thumbnails
                items(popularItemsState ?: emptyList()) { categoryMeal ->
                    ProfileVideo(
                        imageUrl = categoryMeal?.strMealThumb,
                        mealName = categoryMeal?.strMeal ?: "",
                        views = "4.7K",
                        time = "40 min",
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        @Composable
        fun StatItem(label: String, count: String) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = label)
                Text(
                    text = count,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

