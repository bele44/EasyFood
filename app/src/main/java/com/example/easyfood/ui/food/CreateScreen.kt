package com.example.easyfood.ui.food

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyfood.R

import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File


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

        LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button to navigate back
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                // More options icon (for future use or additional options)
                Icon(
                    painter = painterResource(id = R.drawable.more),
                    contentDescription = "More",
                    tint = Color.Red,
                    modifier = Modifier.size(25.dp),
                )
            }
        }

        item {
            // Title of the screen
            Text(text = "Create recipe", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

            item {
                // Display the selected video if available
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
                    )}}
            item {
                // Add the Video of the meal by picking a file
                Button(onClick = { filePickerLauncher.launch("video/*") }) {
                    Text(text = "Select Video")
                }
            }

        item {
            // Add the meal name in an outlined text input field
            BasicTextField(
                value = mealName,
                onValueChange = { mealName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (mealName.text.isEmpty()) {
                        Text(
                            text = "Meal Name: ",
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )
        }
        item {
            // Serves and Cook time texts with an input field for each and an icon before the text
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Serves icon
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Serves Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Serves text
                    Text("Serves: ")
                    Spacer(modifier = Modifier.width(8.dp))
                    // Serves input field
                    BasicTextField(
                        value = serves,
                        onValueChange = { serves = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Gray)
                            .padding(8.dp),
                        decorationBox = { innerTextField ->
                            if (serves.text.isEmpty()) {
                                Text(
                                    text = "Enter Serves",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Cook time icon
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Cook Time Icon",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Cook time text
                    Text("Cook Time: ")
                    Spacer(modifier = Modifier.width(8.dp))
                    // Cook time input field
                    BasicTextField(
                        value = cookTime,
                        onValueChange = { cookTime = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Gray)
                            .padding(8.dp),
                        decorationBox = { innerTextField ->
                            if (cookTime.text.isEmpty()) {
                                Text(
                                    text = "Enter Cook Time",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }

        item {
            // Title of the screen
            Text(text = "Ingredients", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        items(ingredients.size) { index ->
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Ingredient name input field
                    BasicTextField(
                        value = ingredients[index].first,
                        onValueChange = { newValue ->
                            ingredients = ingredients.toMutableList().also {
                                it[index] = it[index].copy(first = newValue)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Gray)
                            .padding(8.dp),
                        decorationBox = { innerTextField ->
                            if (ingredients[index].first.text.isEmpty()) {
                                Text(
                                    text = "Item Name",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    )

                    // Ingredient amount input field
                    BasicTextField(
                        value = ingredients[index].second,
                        onValueChange = { newValue ->
                            ingredients = ingredients.toMutableList().also {
                                it[index] = it[index].copy(second = newValue)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .border(1.dp, Color.Gray)
                            .padding(8.dp),
                        decorationBox = { innerTextField ->
                            if (ingredients[index].second.text.isEmpty()) {
                                Text(
                                    text = "Amount",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Add/Remove icon
                    IconButton(onClick = {
                        ingredients = ingredients.toMutableList().apply {
                            if (size == index + 1) add(Pair(TextFieldValue(""), TextFieldValue(""))) else removeAt(index)
                        }
                    }) {
                        Icon(
                            imageVector = if (ingredients.size == index + 1) Icons.Default.Add else Icons.Default.Delete,
                            contentDescription = if (ingredients.size == index + 1) "Add Icon" else "Remove Icon",
                            tint = if (ingredients.size == index + 1) Color.Green else Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
        item {
            // Add new ingredient button
            Button(colors = ButtonDefaults.buttonColors(Color.White),onClick = {
                ingredients = ingredients.toMutableList().apply { add(Pair(TextFieldValue(""), TextFieldValue(""))) }
            }) {
                Row {
                    Icon(imageVector = Icons.Default.Add, contentDescription ="add", tint = Color.Black )
                    Text("Add new Ingredient",color = Color.Black)
                }

            }
        }
        item {
            // Save my recipes button
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text("Save my recipes", color = Color.White)
            }
        }
    }
}
