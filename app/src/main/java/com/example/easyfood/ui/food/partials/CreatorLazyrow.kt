package com.example.easyfood.ui.food.partials

import androidx.compose.runtime.Composable
import com.example.easyfood.ui.food.Creator

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyfood.ui.navigation.Screen


@Composable
fun CreatorLazyrow(creators: List<Creator>,navController: NavController) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(creators) { creator ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = creator.imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate(Screen.Profile.route) }
                )
                Text(
                    text = creator.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}



