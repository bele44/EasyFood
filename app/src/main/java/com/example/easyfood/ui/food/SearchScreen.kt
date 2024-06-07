package com.example.easyfood.ui.food
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.easyfood.model.Meal
import com.example.easyfood.ui.food.store.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: RecipeViewModel = hiltViewModel(),
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val searchedMealsState = viewModel.searchedMeals.collectAsState()
    val errorState = viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {  OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query
                        viewModel.searchMeal(query.text)
                    },
                    label = { Text("Search Meals...",color = Color.Red) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                        ),
                ) },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            if (errorState.value != null) {
                Text(text = "Error: ${errorState.value}")
            } else {
                searchedMealsState.value.let { meals ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(meals) { meal ->
                            MealItem(meal = meal,navController=navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealItem(meal: Meal, navController:NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                "popular/${meal.idMeal}"
                navController.navigate("popular/${meal.idMeal}")
            }
    ) {
        SubcomposeAsyncImage(
            model = meal.strMealThumb,
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
                .size(64.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )
        meal.strMeal?.let { Text(text = it) }
    }
}
