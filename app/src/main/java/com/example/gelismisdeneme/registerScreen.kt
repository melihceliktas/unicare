package com.example.gelismisdeneme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var studentNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val viewModel = viewModel<AuthViewModel>()
    var authVM = viewModel<JournalViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Giriş Yap", style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.primary
        ))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Ad", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklandığında çerçeve rengi
                unfocusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklanmadığında çerçeve rengi
                cursorColor = MaterialTheme.colorScheme.primary // İmleç rengi
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Soyad", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklandığında çerçeve rengi
                unfocusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklanmadığında çerçeve rengi
                cursorColor = MaterialTheme.colorScheme.primary // İmleç rengi
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = studentNumber,
            onValueChange = { studentNumber = it },
            label = { Text("Öğrenci Numarası", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklandığında çerçeve rengi
                unfocusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklanmadığında çerçeve rengi
                cursorColor = MaterialTheme.colorScheme.primary // İmleç rengi
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklandığında çerçeve rengi
                unfocusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklanmadığında çerçeve rengi
                cursorColor = MaterialTheme.colorScheme.primary // İmleç rengi
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre", color = MaterialTheme.colorScheme.primary) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklandığında çerçeve rengi
                unfocusedBorderColor = MaterialTheme.colorScheme.primary, // Odaklanmadığında çerçeve rengi
                cursorColor = MaterialTheme.colorScheme.primary // İmleç rengi
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() &&
                    lastName.isNotEmpty() && studentNumber.isNotEmpty()
                ) {
                    viewModel.registerUser(
                        email = email,
                        password = password,
                        firstName = firstName,
                        lastName = lastName,
                        studentNumber = studentNumber
                    ) { success, message ->
                        if (success) {

                            onRegisterSuccess()

                        } else {
                            errorMessage = message ?: "Kayıt başarısız!"
                        }
                    }
                } else {
                    errorMessage = "Tüm alanları doldurun!"
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Kayıt Ol",
                        style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 24.sp
                        ),
                color = Color(0xFF352019))
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = errorMessage, color = Color.Red)
        }
    }
}