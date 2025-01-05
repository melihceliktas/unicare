package com.example.gelismisdeneme.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.gelismisdeneme.R
import java.time.format.TextStyle


val robotoFontFamily = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_boldcondensed, FontWeight.Bold),
    Font(R.font.roboto_boldcondenseditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_condensed, FontWeight.Normal),
    Font(R.font.roboto_condenseditalic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_thinitalic, FontWeight.Thin, FontStyle.Italic)
)

//Aşağıdaki yazı tarzını kullanın lütfen, Ağırlıklı kullanılanlar:
// headlineSmall, headlineLarge, bodyLarge
//Örnek Kod:
// Text(
//        "Öğrenci Numarası",
//         style = MaterialTheme.typography.bodyLarge)

val appTypography = Typography(
    headlineSmall = androidx.compose.ui.text.TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp // Farklı font boyutu ekliyoruz
    ),
    headlineMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp // Farklı font boyutu ekliyoruz
    ),
    headlineLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp // Farklı font boyutu ekliyoruz
    ),
    bodySmall = androidx.compose.ui.text.TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal, // Normal font ağırlığı
        fontSize = 12.sp // Küçük metin boyutu
    ),
    bodyMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Normal, // Normal font ağırlığı
        fontSize = 14.sp // Orta metin boyutu
    ),
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = robotoFontFamily,
        fontWeight = FontWeight.Light, // Normal font ağırlığı
        fontSize = 16.sp // Büyük metin boyutu
    )
)



private val DarkColor = darkColorScheme(
    background = kahverengi,
    secondary = krem,
    primary = beyaz,
    surface = sarı
)

private val LightColor = lightColorScheme(
    background = beyaz,
    secondary = krem,
    primary= kahverengi,
    surface = sarı


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun GelismisdenemeTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColor else LightColor

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}