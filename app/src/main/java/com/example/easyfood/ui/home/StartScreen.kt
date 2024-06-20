import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import com.example.easyfood.ui.navigation.Screen
import com.example.easyfood.R
import com.example.easyfood.ui.theme.poppinsFontFamily

@Composable
fun StartScreen(navController: NavController) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight
        val isLargeScreen = maxWidth > 600.dp || maxHeight > 800.dp
        val topPadding = if (isLargeScreen) maxHeight * 0.4f else maxHeight * 0.3f
        val fontSizeLarge = if (isLargeScreen) 36.sp else 24.sp
        val fontSizeMedium = if (isLargeScreen) 18.sp else 14.sp
        val buttonPadding = if (isLargeScreen) 30.dp else 16.dp

        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(if (isLargeScreen) 30.dp else 25.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.premium_recipes),
                        color = Color.White,
                        fontSize = fontSizeMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topPadding)
            ) {
                Text(
                    text = stringResource(id = R.string.lets),
                    color = Color.White,
                    fontSize = fontSizeLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(id = R.string.cooking),
                    color = Color.White,
                    fontSize = fontSizeLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.find_best_recipe),
                    color = Color.White,
                    fontSize = fontSizeMedium,
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = poppinsFontFamily
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = buttonPadding),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = { navController.navigate(Screen.Recipes.route) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(id = R.string.start_cooking))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}
