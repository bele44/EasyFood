package com.example.easyfood.ui.food.partials

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.easyfood.R
import com.example.easyfood.utils.truncateText

import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun RecentRecipe(imageUrl: String?, mealName: String?, navController: NavController) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Gray)
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(130.dp)
                    .width(180.dp).clickable {
                        navController.navigate("make")
                    }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color(0x80000000), RoundedCornerShape(4.dp))
                    .align(Alignment.TopStart)
                    .padding( 8.dp)
            ) {

            }

        }
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "${truncateText(mealName ?: "", 13)}",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 0.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = " by salman",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}



