package com.example.gelismisdeneme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.CircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationScreen() {
    var selectedMinutes by remember { mutableStateOf(5) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableStateOf(5 * 60) }
    var showBreathingGuide by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Breathing animation
    val breatheInfiniteTransition = rememberInfiniteTransition()
    val breathScale by breatheInfiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Timer effect
    LaunchedEffect(isTimerRunning, remainingSeconds) {
        if (isTimerRunning && remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds--
        } else if (remainingSeconds == 0) {
            isTimerRunning = false
            showBreathingGuide = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFEF9F0),
                        Color(0xFFF5E6D3)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Welcome Section
            Text(
                text = "Meditasyon",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                ),
                color = Color(0xFF352019)
            )
            
            Text(
                text = "Zihinsel huzur için günlük meditasyon pratiği",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF352019).copy(alpha = 0.8f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Timer Card with Breathing Guide
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF352019))
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Breathing Circle Animation
                    if (showBreathingGuide && isTimerRunning) {
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .scale(breathScale)
                                .background(
                                    color = Color(0xFFFEF9F0).copy(alpha = 0.2f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (breathScale > 1f) "Nefes Al" else "Nefes Ver",
                                color = Color(0xFFFEF9F0),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = formatTime(remainingSeconds),
                        style = MaterialTheme.typography.displayLarge,
                        color = Color(0xFFFEF9F0)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Timer Duration Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(5, 10, 15, 20).forEach { minutes ->
                            OutlinedButton(
                                onClick = { 
                                    selectedMinutes = minutes
                                    remainingSeconds = minutes * 60
                                    isTimerRunning = false
                                    showBreathingGuide = false
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = if (selectedMinutes == minutes) 
                                        Color(0xFFFEF9F0) else Color(0xFFFEF9F0).copy(alpha = 0.6f)
                                ),
                                border = BorderStroke(1.dp, Color(0xFFFEF9F0))
                            ) {
                                Text("${minutes}dk")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Control Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { 
                                isTimerRunning = !isTimerRunning
                                if (isTimerRunning) showBreathingGuide = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEF9F0))
                        ) {
                            Text(
                                if (!isTimerRunning) "Başlat" else "Duraklat",
                                color = Color(0xFF352019)
                            )
                        }

                        Button(
                            onClick = { 
                                isTimerRunning = false
                                remainingSeconds = selectedMinutes * 60
                                showBreathingGuide = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEF9F0))
                        ) {
                            Text("Sıfırla", color = Color(0xFF352019))
                        }
                    }

                    // Breathing Guide Toggle
                    Switch(
                        checked = showBreathingGuide,
                        onCheckedChange = { showBreathingGuide = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFFFEF9F0),
                            checkedTrackColor = Color(0xFFFEF9F0).copy(alpha = 0.5f)
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = "Nefes Rehberi",
                        color = Color(0xFFFEF9F0),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Quick Tips Section
            QuickTipsSection()

            // Meditation Techniques Section
            Text(
                text = "Meditasyon Teknikleri",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF352019),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            MeditationTechniqueCard(
                title = "Nefes Meditasyonu",
                description = "1. Rahat bir pozisyonda oturun\n" +
                    "2. Gözlerinizi nazikçe kapatın\n" +
                    "3. Doğal nefes akışınıza odaklanın\n" +
                    "4. Her nefeste 4 saniye alın, 4 saniye tutun, 4 saniye verin\n" +
                    "5. Zihniniz dağıldığında nazikçe nefese geri dönün\n" +
                    "6. Düzenli pratik için günde 5-10 dakika ayırın",
                icon = Icons.Default.Air
            )

            MeditationTechniqueCard(
                title = "Beden Taraması",
                description = "1. Sırt üstü uzanın veya rahat bir pozisyon alın\n" +
                    "2. Ayak parmaklarınızdan başlayarak vücudunuzu tarayın\n" +
                    "3. Her bölgeye 20-30 saniye odaklanın\n" +
                    "4. Gerginlikleri fark edin ve bilinçli olarak gevşetin\n" +
                    "5. Vücudunuzun her bölgesini sevgiyle kabul edin\n" +
                    "6. Başınıza kadar ilerleyin ve tüm vücudunuzu hissedin",
                icon = Icons.Default.Person
            )

            MeditationTechniqueCard(
                title = "Sevgi-Şefkat Meditasyonu",
                description = "1. Rahat bir oturma pozisyonu alın\n" +
                    "2. Kendinize şefkat ve sevgi dileyin\n" +
                    "3. Sevdiklerinizi düşünün ve onlara iyi dilekler gönderin\n" +
                    "4. Tarafsız olduğunuz kişilere sevgi dileyin\n" +
                    "5. Zor ilişkileriniz olan kişilere şefkat gönderin\n" +
                    "6. Tüm canlıları kapsayan evrensel bir sevgi dileyin",
                icon = Icons.Default.Favorite
            )

            // Benefits Section
            BenefitsSection()
        }
    }
}

@Composable
fun QuickTipsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9F0))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Hızlı İpuçları",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF352019)
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            listOf(
                "Düzenli bir meditasyon rutini oluşturun",
                "Sessiz ve rahat bir ortam seçin",
                "Yargılamadan gözlemleyin",
                "Sabırlı olun, acele etmeyin",
                "Düşüncelerinizi kontrol etmeye çalışmayın"
            ).forEach { tip ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color(0xFF352019),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = tip,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF352019)
                    )
                }
            }
        }
    }
}

@Composable
fun MeditationTechniqueCard(title: String, description: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9F0))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF352019),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF352019)
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF352019).copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun BenefitsSection() {
    Text(
        text = "Meditasyonun Faydaları",
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        color = Color(0xFF352019),
        modifier = Modifier.padding(vertical = 16.dp)
    )

    val benefits = listOf(
        Triple(Icons.Default.Psychology, "Stres Yönetimi", "Günlük stres ve kaygıyı azaltır"),
        Triple(Icons.Default.Lightbulb, "Odaklanma", "Dikkat ve konsantrasyonu artırır"),
        Triple(Icons.Default.Favorite, "Duygusal Denge", "Duygu kontrolünü geliştirir"),
        Triple(Icons.Default.Refresh, "Uyku Kalitesi", "Daha iyi uyku düzeni sağlar")
    )

    benefits.forEach { (icon, title, description) ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9F0))
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF352019),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color(0xFF352019)
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF352019).copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
} 