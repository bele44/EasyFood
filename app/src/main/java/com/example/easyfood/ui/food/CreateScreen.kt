package com.example.easyfood.ui.food
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

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
                fontSize = 24.sp,
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
            Button(onClick = { filePickerLauncher.launch("video/*") }) {
                Text(text = stringResource(R.string.select_video))
            }
        }

        item {
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
                            text = stringResource(R.string.meal_name),
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )
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
                                    text = stringResource(R.string.enter_serves),
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = stringResource(R.string.add_icon),
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.cook_time))
                    Spacer(modifier = Modifier.width(8.dp))
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
                                    text = stringResource(R.string.enter_cook_time),
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
            Text(
                text = stringResource(R.string.ingredients),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(ingredients.size) { index ->
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                                    text = stringResource(R.string.item_name),
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    )

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
                                    text = stringResource(R.string.amount),
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
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
                colors = ButtonDefaults.buttonColors(Color.White),
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
