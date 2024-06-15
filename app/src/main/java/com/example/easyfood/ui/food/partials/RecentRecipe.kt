package com.example.easyfood.ui.food.partials

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import coil.compose.SubcomposeAsyncImage
import com.example.easyfood.R
import com.example.easyfood.utils.truncateText
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.easyfood.ui.navigation.Screen

@Composable
fun RecentRecipe(imageUrl: String?, mealName: String?, navController: NavController) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val maxWidth = maxWidth
        val isLargeScreen = maxWidth > 600.dp
        val imageHeight: Dp = if (isLargeScreen) 130.dp else 100.dp
        val imageWidth: Dp = if (isLargeScreen) 180.dp else 130.dp
        val fontSize: TextUnit = if (isLargeScreen) 16.sp else 14.sp

        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { navController.navigate(Screen.Make.route) }
            ) {
                SubcomposeAsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(imageHeight)
                        .width(imageWidth)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0x80000000), RoundedCornerShape(4.dp))
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    // Add any additional content here
                }
            }
            Column(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = truncateText(mealName ?: "", 13),
                    fontSize = fontSize,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 0.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.by_salman),
                        fontSize = fontSize * 0.93f,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
