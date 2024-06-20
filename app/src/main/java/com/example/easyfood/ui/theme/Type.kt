package com.example.easyfood.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.easyfood.R
val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_black, FontWeight.Black),
    Font(R.font.poppins_blackitalic, FontWeight.Black),
    Font(R.font.poppins_bold, FontWeight.Bold),
    Font(R.font.poppins_bolditalic, FontWeight.Bold),
    Font(R.font.poppins_extrabold, FontWeight.ExtraBold),
    Font(R.font.poppins_extrabolditalic, FontWeight.ExtraBold),
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_thin, FontWeight.Thin),
    Font(R.font.poppins_extralight, FontWeight.ExtraLight),
    Font(R.font.poppins_extralightitalic, FontWeight.ExtraLight),
    Font(R.font.poppins_light, FontWeight.Light),
    Font(R.font.poppins_lightitalic, FontWeight.Light),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_mediumitalic, FontWeight.Medium),
    Font(R.font.poppins_semibolditalic, FontWeight.SemiBold),

    )

val typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
     headlineLarge = TextStyle(
         fontFamily = poppinsFontFamily,
         fontWeight = FontWeight.Bold,
         fontSize = 20.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = poppinsFontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
    ),
            labelSmall = TextStyle(
            fontFamily = poppinsFontFamily,
    fontWeight = FontWeight.Light,
    fontSize = 12.sp,
)
)
