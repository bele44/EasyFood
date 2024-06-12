package com.example.easyfood.ui.food.partials

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.example.easyfood.R

@Composable
fun ProfileVideo(imageUrl: String?, mealName: String?, views: String, time: String,navController: NavController) {
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
                    .height(150.dp)
                    .fillMaxWidth()
            )
            val context = LocalContext.current

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier

                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            ) {
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/watch?v=1IszT_guI08")
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(2.dp)
                        .size(30.dp),
                    containerColor = Color.White
                ){
                    Icon(

                        painter = painterResource(id = R.drawable.more),
                        contentDescription = "More",
                        tint = Color.Red,
                        modifier = Modifier.size(25.dp),

                        )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "4.5",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Column( modifier = Modifier
                .align(Alignment.BottomStart)) {
                Text(
                    text = mealName?:"",
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        // .padding(8.dp)
                        //.background(Color(0x80000000), RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp)
                )
                Text(text = "$views views | $time",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom=8.dp, start = 8.dp))

            }

        }


    }
}