package com.example.easyfood.ui.food

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(viewModel: RecipeViewModel = hiltViewModel(), navController: NavController) {
    val popularItemsState by viewModel.popularItems.collectAsState()
    var selected by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = stringResource(id = R.string.saved_recipes),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                    }
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

        Column(modifier = Modifier.fillMaxWidth().padding(8.dp).padding(innerPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp).background(
                        color = if (selected == "video") Color.Red else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ).clickable {
                        selected = "video"
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.tab_video),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selected == "video") Color.White else Color.Red,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp).background(
                        color = if (selected == "Recipes") Color.Red else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    ).clickable {
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

            Spacer(modifier = Modifier.height(8.dp))

            // Over Popular Meals LazyColumn
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
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
