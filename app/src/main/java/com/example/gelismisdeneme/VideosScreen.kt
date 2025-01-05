package com.example.gelismisdeneme

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter

data class Video(val title: String, val url: String, val thumbnailUrl: String)

@Composable
fun VideosScreen(navController: NavController) {
    val videos = listOf(
        Video(
            "Nasıl Daha Huzurlu Olabilirsin ?",
            "https://youtu.be/vHlkA93KBv8?si=nn3UiK-rapi-aip8",
            "https://img.youtube.com/vi/vHlkA93KBv8/0.jpg"
        ),
        Video(
            "Hassas ve Duygusal İnsanlar Nasıl Daha Güçlü Olabilir ?",
            "https://youtu.be/2CLLTk3qmuU?si=VRaEnZfkMuDa4Yty",
            "https://img.youtube.com/vi/2CLLTk3qmuU/0.jpg"
        ),
        Video(
            "Ruhsal Yorgunluk Nasıl Geçer ?",
            "https://youtu.be/QftpzM70cGI?si=JwEcnf4NqI0ZY4zC",
            "https://img.youtube.com/vi/QftpzM70cGI/0.jpg"
        ),
        Video(
            "Kafaya Takmama Sanatı",
            "https://youtu.be/3rXPVw_qYqE?si=mynIXT7Ke58u2nFI",
            "https://img.youtube.com/vi/3rXPVw_qYqE/0.jpg"
        ),
        Video(
            "Dopamin Detoksu",
            "https://youtu.be/mDAO_vHOjXs?si=tJiUR1Gk5eXvkl47",
            "https://img.youtube.com/vi/mDAO_vHOjXs/0.jpg"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
    ) {
        // Health Label
        Text(
            text = "Sağlık",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp
            ),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
            textAlign = TextAlign.Start
        )


        // Video List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(videos) { video ->
                VideoCard(video = video, onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.url))
                    navController.context.startActivity(intent)
                })
            }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary) // Single solid background color
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thumbnail Image
                AsyncImage(
                    model = video.thumbnailUrl,
                    contentDescription = video.title,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Video Title
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background,
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    }
}
