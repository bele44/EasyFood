package com.example.easyfood.ui.food

import android.icu.text.ListFormatter.Width
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyfood.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCreation(navController: NavController) {
    var videoLink by remember { mutableStateOf(TextFieldValue("")) }
    var mealName by remember { mutableStateOf(TextFieldValue("")) }
    var serves by remember { mutableStateOf(TextFieldValue("")) }
    var cookTime by remember { mutableStateOf(TextFieldValue("")) }
    var ingredients by remember { mutableStateOf(listOf(Pair(TextFieldValue(""), TextFieldValue("")))) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val filePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            videoUri = it
        }
    }

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Gray,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        cursorColor = Color.Black
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
        ,
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon),
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = stringResource(R.string.more),
                    tint = Color.Red,
                    modifier = Modifier.size(25.dp),
                )
            }
        }

        item {
            Text(
                text = stringResource(R.string.create_recipe),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            videoUri?.let { uri ->
                AndroidView(
                    factory = { context ->
                        VideoView(context).apply {
                            setVideoURI(uri)
                            start()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(150.dp)
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }

        item {
            Button(onClick = { filePickerLauncher.launch("video/*") }, modifier = Modifier
                .padding(8.dp)
                .width(140.dp)
                .height(40.dp),
                colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = stringResource(R.string.select_video))
            }
        }

        item {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.meal_name),
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.meal),)
                Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = mealName,
                onValueChange = { mealName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp),
                label = { Text(text = stringResource(R.string.meal_name), color = Color.LightGray) },
                shape = RoundedCornerShape(8.dp),
                colors = textFieldColors
            )
        }
        }

        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(R.string.add_icon),
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.serves))
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = serves,
                        onValueChange = { serves = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 0.dp),
                        label = { Text(text = stringResource(R.string.enter_serves), color  = Color.LightGray) },
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = stringResource(R.string.add_icon),
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(stringResource(R.string.time))
                    Spacer(modifier = Modifier.width(20.dp))
                    OutlinedTextField(
                        value = cookTime,
                        onValueChange = { cookTime = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 0.dp),
                        label = { Text(text = stringResource(R.string.enter_cook_time), color = Color.LightGray) },
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    )
                }
            }
        }

        item {
            Text(
                text = stringResource(R.string.ingredients),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(ingredients.size) { index ->
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = ingredients[index].first,
                        onValueChange = { newValue ->
                            ingredients = ingredients.toMutableList().also {
                                it[index] = it[index].copy(first = newValue)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 0.dp),
                        label = { Text(text = stringResource(R.string.item_name), color = Color.LightGray) },
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                      
                    OutlinedTextField(
                        value = ingredients[index].second,
                        onValueChange = { newValue ->
                            ingredients = ingredients.toMutableList().also {
                                it[index] = it[index].copy(second = newValue)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 0.dp),
                        label = { Text(text = stringResource(R.string.amount), color = Color.LightGray) },
                        shape = RoundedCornerShape(8.dp),
                        colors = textFieldColors
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        ingredients = ingredients.toMutableList().apply {
                            if (size == index + 1) add(Pair(TextFieldValue(""), TextFieldValue(""))) else removeAt(index)
                        }
                    }) {
                        Icon(
                            imageVector = if (ingredients.size == index + 1) Icons.Default.Add else Icons.Default.Delete,
                            contentDescription = if (ingredients.size == index + 1) stringResource(R.string.add_icon) else stringResource(R.string.remove_icon),
                            tint = if (ingredients.size == index + 1) Color.Green else Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        item {
            Button(
                modifier = Modifier.padding(4.dp),
                colors = ButtonDefaults.buttonColors(Color.LightGray),
                onClick = {
                    ingredients = ingredients.toMutableList().apply { add(Pair(TextFieldValue(""), TextFieldValue(""))) }
                }
            ) {
                Row {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon", tint = Color.Black)
                    Text(stringResource(R.string.add_ingredient), color = Color.Black)
                }
            }
        }

        item {
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(stringResource(R.string.save_recipes), color = Color.White)
            }
        }
    }
}
