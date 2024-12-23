package com.example.gelismisdeneme


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BoxItemBook("Günlük") { navController.navigate("details/1") }
            BoxItemHealth("Sağlık") { navController.navigate("details/2") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BoxItemMeditation("Meditasyon") { navController.navigate("details/3") }
            BoxItemAppointment("Randevu") { navController.navigate("details/4") }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BoxItemSettings("Ayarlar") { navController.navigate("details/5") }
            BoxItemWithIcon("Profil", onClick = { navController.navigate("details/6") })
        }
    }
}

@Composable
fun BoxItem(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
@Composable
fun EmptyPage(modifier: Modifier = Modifier) {
    // Boş bir sayfa (Column içinde boş bir alan)
    Column(
        modifier = modifier
            .fillMaxSize()  // Sayfanın tamamını kaplaması için
            .padding(16.dp),  // Dışarıdan biraz boşluk eklemek için
    ) {
        // Burada herhangi bir içerik bulunmuyor, boş bir alan var.
        // Eğer içerik eklemek isterseniz buraya ekleyebilirsiniz.
    }
}

// Profil ikonu eklenmiş BoxItem
@Composable
fun BoxItemWithIcon(label: String, onClick: () -> Unit)  { //Profil iconu
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Profil ikonu ve metin
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            /*Icon(
                imageVector = Icons.Filled.Person, // Material3 profil ikonu
                contentDescription = "Profil Ikonu",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
            */

            Image(
                painter = painterResource(id = R.drawable.profil),
                contentDescription = "Profil Logosu",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillHeight
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun BoxItemBook(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Kitap ikonu ve metin
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            /*Icon(
                imageVector = Icons.Filled.MenuBook, // Kitap ikonu
                contentDescription = "Kitap Ikonu",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )*/

            Image(
                painter = painterResource(id = R.drawable.gunluk),
                contentDescription = "Günlük Logosu",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillHeight
            )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun BoxItemHealth(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Image(
                painter = painterResource(id = R.drawable.saglik),
                contentDescription = "Saglik Logosu",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillHeight
            )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun BoxItemMeditation(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.meditasyon),
                contentDescription = "Meditasyon Logosu",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillWidth
            )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun BoxItemAppointment(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.randevu),
                contentDescription = "Randevu Logosu",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillHeight
            )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun BoxItemSettings(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(175.dp)
            .background(
                color = Color(0xFF352019),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Image(
                painter = painterResource(id = R.drawable.ayarlar),
                contentDescription = "Ayarlar Logosu",
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.FillHeight
            )


            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}