package com.example.easyfood.ui.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.easyfood.R
import com.example.easyfood.ui.food.partials.MakingProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakingFood(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.how_to_make_french_toast),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                MakingProcess(navController = navController)
            }
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.ingredients),
                            fontSize = 22.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 4.dp)
                        )

                        Text(text = stringResource(R.string.items_count, 5), color = Color.Black)
                    }
                    IngredientCard(
                        imageResId = R.drawable.toast,
                        ingredientName = stringResource(R.string.bread),
                        quantity = stringResource(R.string.grams, 200)
                    )
                    IngredientCard(
                        imageResId = R.drawable.eggs,
                        ingredientName = stringResource(R.string.eggs),
                        quantity = stringResource(R.string.grams, 200)
                    )
                    IngredientCard(
                        imageResId = R.drawable.toast,
                        ingredientName = stringResource(R.string.milk),
                        quantity = stringResource(R.string.grams, 200)
                    )
                }
            }
        }
    }
}

    @Composable
    fun IngredientCard(imageResId: Int, ingredientName: String, quantity: String) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = ingredientName,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }

                Text(
                    text = quantity,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }
    }
