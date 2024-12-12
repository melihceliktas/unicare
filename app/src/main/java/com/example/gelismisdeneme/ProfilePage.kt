package com.example.gelismisdeneme

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    viewModel: ProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel(),
    onSignOut: () -> Unit // Giriş ekranına yönlendirme için callback
) {
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val studentNumber by viewModel.studentNumber.collectAsState()
    val email by viewModel.email.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val textFieldColors = TextFieldDefaults.textFieldColors(
        containerColor = MaterialTheme.colorScheme.surface, // TextField'ın arka plan rengi
        focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Odaklanıldığında alt çizgi rengi
        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant, // Odaklanılmadığında alt çizgi rengi
        focusedLabelColor = MaterialTheme.colorScheme.primary, // Etiket rengi odaklandığında
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant // Etiket rengi odaklanılmadığında
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,

    ) {
        // Profil Başlık
        Text(
            text = "Profilim",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp // Başlık boyutunu artırdık
            ),
            modifier = Modifier.padding(top = 150.dp, bottom = 16.dp)
        )

        // Ad TextField
        TextField(
            value = firstName,
            onValueChange = { viewModel.updateFirstName(it) },
            label = {
                Text(
                    "Ad",
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

        // Soyad TextField
        TextField(
            value = lastName,
            onValueChange = { viewModel.updateLastName(it) },
            label = {
                Text(
                    "Soyad",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            colors = textFieldColors,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Öğrenci Numarası TextField
        TextField(
            value = studentNumber,
            onValueChange = { viewModel.updateStudentNumber(it) },
            label = {
                Text(
                    "Öğrenci Numarası",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            colors = textFieldColors,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // E-posta TextField (Düzenlenemez)
        TextField(
            value = email,
            onValueChange = {},
            label = {
                Text(
                    "E-posta",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            enabled = false // E-posta düzenlenemez
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bilgileri Güncelle butonu
        Button(
            onClick = {
                viewModel.saveUserData()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Bilgileri Güncelle")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Çıkış Yap butonu
        Button(
            onClick = {

                authViewModel.signOut() // Oturumu kapat
                onSignOut() // Giriş ekranına yönlendir

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Çıkış Yap")
        }

        // Hata mesajı
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
    }
}