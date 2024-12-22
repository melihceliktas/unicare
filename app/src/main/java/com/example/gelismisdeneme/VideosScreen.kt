package com.example.gelismisdeneme

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Video(val title: String, val url: String)

@Composable
fun VideosScreen(navController: NavController) {
    val videos = listOf(
        Video("Nasıl Daha Huzurlu olabilirsin ?", "https://youtu.be/vHlkA93KBv8?si=nn3UiK-rapi-aip8"),
        Video("Hassas ve Duygusal İnsanlar Nasıl Daha Güçlü Olabilir ?", "https://youtu.be/2CLLTk3qmuU?si=VRaEnZfkMuDa4Yty"),
        Video("Ruhsal Yorgunluk Nasıl Geçer ? ", "https://youtu.be/QftpzM70cGI?si=JwEcnf4NqI0ZY4zC"),
        Video("Kafaya Takmama Sanatı", "https://youtu.be/3rXPVw_qYqE?si=mynIXT7Ke58u2nFI"),
        Video("Dopamin Detoksu", "https://youtu.be/mDAO_vHOjXs?si=tJiUR1Gk5eXvkl47")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(videos) { video ->
            VideoCard(video = video, onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                navController.context.startActivity(intent)
            })
        }
    }
}

@Composable
fun VideoCard(video: Video, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6200EE)),
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = video.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}