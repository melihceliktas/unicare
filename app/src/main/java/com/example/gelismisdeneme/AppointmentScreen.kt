package com.example.gelismisdeneme

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    viewModel: AppointmentViewModel = viewModel()
) {
    var showCalendly by remember { mutableStateOf(false) }
    val appointments by viewModel.appointments.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Psikolojik Danışmanlık",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            ),
            color = Color(0xFF352019),
            modifier = Modifier.padding(top = 60.dp, bottom = 8.dp)
        )

        // Description
        Text(
            text = "Üniversite hayatında karşılaştığınız zorlukları birlikte aşalım",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF352019),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Info Cards
        InfoCard(
            icon = Icons.Default.Person,
            title = "Uzman Psikolog",
            description = "Deneyimli uzman psikologlarımız ile birebir görüşme imkanı"
        )

        InfoCard(
            icon = Icons.Default.LocationOn,
            title = "Görüşme Yeri",
            description = "Üniversite Sağlık Merkezi, B Blok, 2. Kat"
        )

        InfoCard(
            icon = Icons.Default.Info,
            title = "Önemli Bilgiler",
            description = "• İlk görüşme 45 dakika sürmektedir\n" +
                    "• Randevunuza 5 dakika önce gelmeniz önerilir\n" +
                    "• Görüşmeler gizlilik esasına dayanır\n" +
                    "• Acil durumlar için 7/24 destek hattı: 0850 XXX XX XX"
        )

        // Calendly Button
        Button(
            onClick = { showCalendly = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF352019)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Randevu Planla",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 24.sp
                ),
                color = Color(0xFFFEF9F0),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // Upcoming Appointments Section
        if (appointments.isNotEmpty()) {
            Text(
                text = "Yaklaşan Randevularınız",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                ),
                color = Color(0xFF352019),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(appointments) { appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onDelete = { viewModel.deleteAppointment(appointment) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    // Calendly Dialog
    if (showCalendly) {
        AlertDialog(
            onDismissRequest = { showCalendly = false },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            properties = DialogProperties(usePlatformDefaultWidth = false),
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    CalendlyWebView(
                        onClose = { showCalendly = false }
                    )
                    IconButton(
                        onClick = { showCalendly = false },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF352019)
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFEF9F0)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF352019),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF352019)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF352019)
                )
            }
        }
    }
}

@Composable
fun CalendlyWebView(onClose: () -> Unit) {
    val context = LocalContext.current
    
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl("https://calendly.com/devtex16/30min")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun AppointmentCard(
    appointment: AppointmentEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFEF9F0)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Tarih: ${appointment.date}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF352019)
            )
            Text(
                text = "Saat: ${appointment.time}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF352019)
            )
            Text(
                text = "Neden: ${appointment.reason}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF352019)
            )
            
            Button(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF352019))
            ) {
                Text("İptal Et", color = Color(0xFFFEF9F0))
            }
        }
    }
} 