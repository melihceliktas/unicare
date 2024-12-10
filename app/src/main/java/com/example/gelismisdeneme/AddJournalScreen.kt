package com.example.gelismisdeneme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
        containerColor = MaterialTheme.colorScheme.surface, // TextField'ın arka plan rengi
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
                fontSize = 28.sp // Başlık boyutunu artırdık
            ),
            modifier = Modifier.padding(top = 150.dp, bottom = 16.dp)
        )

        // Başlık TextField
        TextField(
            value = title,
            onValueChange = { title = it },
            label = {
                Text(
                    "Başlık",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,        // Font boyutunu büyütme
                        fontWeight = FontWeight.Bold // Yazıyı bold yapma
                    )
                )
            },
            colors = textFieldColors,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth()
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
                        fontSize = 16.sp,        // Font boyutunu büyütme
                        fontWeight = FontWeight.Bold // Yazıyı bold yapma
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), // İçeriğin yüksekliğini artırdık
            colors = textFieldColors,

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
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Kaydet")
        }

        // Hata mesajı
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}