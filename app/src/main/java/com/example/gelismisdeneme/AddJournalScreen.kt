package com.example.gelismisdeneme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJournalScreen(viewModel: JournalViewModel, onSaveSuccess: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = Color(0xFFFEF9F0), // TextField'ın arka plan rengi
        focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Odaklanıldığında alt çizgi rengi
        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant, // Odaklanılmadığında alt çizgi rengi
        focusedLabelColor = MaterialTheme.colorScheme.primary, // Etiket rengi odaklandığında
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant // Etiket rengi odaklanılmadığında
    )

    Column(modifier = Modifier.padding(16.dp)) {
        // Başlık
        Text(
            "Yeni Günlük Ekle",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp // Başlık boyutunu artırdık
            ),
            color = Color(0xFF352019),
            modifier = Modifier.padding(top = 125.dp, bottom = 16.dp).padding(horizontal = 16.dp),

        )

        // Başlık TextField
        TextField(
            value = title,
            onValueChange = { title = it },
            label = {
                Text(
                    "Başlık",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 30.sp,        // Font boyutunu büyütme
                        color = Color(0xFF352019)
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            },
            colors = textFieldColors,
            textStyle = TextStyle(color = Color(0xFF352019), fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // İçerik TextField
        TextField(
            value = content,
            onValueChange = { content = it },
            label = {
                Text(
                    "İçerik",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 30.sp,        // Font boyutunu büyütme
                        color = Color(0xFF352019)
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(200.dp), // İçeriğin yüksekliğini artırdık
            colors = textFieldColors,
            textStyle = TextStyle(color = Color(0xFF352019),fontSize = 18.sp),

        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kaydet butonu
        Button(
            onClick = {
                // Veri doğrulama
                if (title.isNotEmpty() && content.isNotEmpty()) {
                    // Firebase'e veri ekle
                    viewModel.addJournalEntry(title, content) { success ->
                        if (success) {
                            onSaveSuccess() // Kaydetme başarılı ise
                        } else {
                            errorMessage = "Günlük kaydedilemedi."
                        }
                    }
                } else {
                    errorMessage = "Lütfen tüm alanları doldurun."
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF352019)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Kaydet",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 24.sp

                ),
                color = Color(0xFFFEF9F0)
            )
        }

        // Hata mesajı
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}