@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentScreen(
    viewModel: AppointmentViewModel = viewModel()
) {
    var showCalendly by remember { mutableStateOf(false) }
    val appointments by viewModel.appointments.collectAsState()
    val scrollState = rememberScrollState()

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
            WelcomeSection()
            
            // Services Section
            ServicesSection()
            
            // Appointment Booking Section
            AppointmentBookingSection(
                onBookClick = { showCalendly = true }
            )
            
            // FAQ Section
            FAQSection()
            
            // Upcoming Appointments Section
            if (appointments.isNotEmpty()) {
                UpcomingAppointmentsSection(
                    appointments = appointments,
                    onDelete = { viewModel.deleteAppointment(it) }
                )
            }
        }
    }

    // Calendly Dialog
    if (showCalendly) {
        CalendlyDialog(
            onDismiss = { showCalendly = false }
        )
    }
}

@Composable
fun WelcomeSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Psikolojik Danışmanlık",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            ),
            color = Color(0xFF352019)
        )
        
        Text(
            text = "Üniversite hayatında karşılaştığınız zorlukları birlikte aşalım",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFF352019).copy(alpha = 0.8f),
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard("500+", "Öğrenci", Icons.Default.School)
            StatCard("10+", "Uzman", Icons.Default.Psychology)
            StatCard("98%", "Memnuniyet", Icons.Default.CheckCircle)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatCard(number: String, label: String, icon: ImageVector) {
    Card(
        modifier = Modifier.padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF352019))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFFEF9F0),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = number,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFEF9F0)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFFEF9F0)
            )
        }
    }
}

@Composable
fun ServicesSection() {
    Text(
        text = "Hizmetlerimiz",
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        color = Color(0xFF352019),
        modifier = Modifier.padding(vertical = 16.dp)
    )

    val services = listOf(
        Triple(Icons.Default.Psychology, "Bireysel Danışmanlık", "Kişisel gelişim ve sorunlarınız için birebir görüşmeler"),
        Triple(Icons.Default.School, "Akademik Danışmanlık", "Eğitim hayatınızdaki zorlukları aşmanız için destek"),
        Triple(Icons.Default.AccessTime, "Kriz Danışmanlığı", "Acil durumlar için 7/24 destek hizmeti")
    )

    services.forEach { (icon, title, description) ->
        ServiceCard(icon, title, description)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceCard(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9F0)),
        border = BorderStroke(1.dp, Color(0xFF352019).copy(alpha = 0.1f))
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
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF352019).copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun AppointmentBookingSection(onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF352019))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Randevu Planlayın",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFFFEF9F0)
            )
            
            Text(
                text = "İlk görüşme ücretsizdir",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFFEF9F0).copy(alpha = 0.8f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = onBookClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEF9F0)),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(
                    "Hemen Randevu Al",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF352019)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ContactInfo(Icons.Default.Email, "Email", "destek@unicare.com")
                ContactInfo(Icons.Default.Phone, "Telefon", "0850 XXX XX XX")
            }
        }
    }
}

@Composable
fun ContactInfo(icon: ImageVector, title: String, info: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFEF9F0),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFFEF9F0).copy(alpha = 0.8f)
        )
        Text(
            text = info,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFFEF9F0)
        )
    }
}

@Composable
fun FAQSection() {
    Text(
        text = "Sıkça Sorulan Sorular",
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold
        ),
        color = Color(0xFF352019),
        modifier = Modifier.padding(vertical = 16.dp)
    )

    val faqs = listOf(
        "Görüşmeler ne kadar sürüyor?" to "İlk görüşme 45 dakika, takip görüşmeleri 30-40 dakika sürmektedir.",
        "Görüşmeler ücretli mi?" to "İlk görüşme ücretsizdir. Sonraki görüşmeler için öğrenci indirimi uygulanmaktadır.",
        "Randevumu nasıl iptal edebilirim?" to "Görüşmeden 24 saat öncesine kadar randevunuzu iptal edebilirsiniz."
    )

    faqs.forEach { (question, answer) ->
        FAQCard(question, answer)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FAQCard(question: String, answer: String) {
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
                text = question,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF352019)
            )
            Text(
                text = answer,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF352019).copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun UpcomingAppointmentsSection(
    appointments: List<AppointmentEntity>,
    onDelete: (AppointmentEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9F0))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Yaklaşan Randevularınız",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF352019),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (appointments.isEmpty()) {
                Text(
                    text = "Henüz planlanmış randevunuz bulunmamaktadır.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF352019).copy(alpha = 0.8f)
                )
            } else {
                appointments.forEach { appointment ->
                    AppointmentCard(
                        appointment = appointment,
                        onDelete = { onDelete(appointment) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendlyDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                CalendlyWebView(
                    onClose = onDismiss
                )
                IconButton(
                    onClick = onDismiss,
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

@OptIn(ExperimentalMaterial3Api::class)
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