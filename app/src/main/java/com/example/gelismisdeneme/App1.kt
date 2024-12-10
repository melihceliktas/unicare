package com.example.gelismisdeneme
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun NameList(modifier: Modifier = Modifier) {
    val viewModel: app1viewModel = viewModel()

    // Firebase'den veri çekmek için fetchUserNames fonksiyonunu çağırmak
    LaunchedEffect(Unit) {
        viewModel.fetchUserNames()  // Firebase'den verileri çek
    }

    // Column: İçeriklerin düzenli şekilde yerleştirildiği bir container
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, top = 100.dp)
    ) {
        // Kullanıcı adı ekleme kısmı (OutlinedTextField + Button)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray)
        ) {
            OutlinedTextField(
                value = viewModel.name,  // viewModel.name, TextField'in değerini tutar
                onValueChange = { newText ->
                    viewModel.name = newText  // Kullanıcı her yazdığında viewModel.name güncellenir
                },
                label = { Text("Enter Name") } // TextBox'a bir label ekliyoruz
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                viewModel.add()  // Firebase'e yeni isim ekler
            }) {
                Text(text = "Add")
            }
        }

        // Verileri listele (LazyColumn ile verileri dinamik bir şekilde göster)
        LazyColumn(modifier = modifier) {
            items(viewModel.names) { currentName ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = currentName,
                        modifier = Modifier.weight(1f)  // İsim sol tarafa yaslanır
                    )

                    // Silme butonu
                    IconButton(onClick = {
                        // Silme işlemini ViewModel'e çağır
                        viewModel.deleteName(currentName)
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
                Divider()
            }
        }
    }
}

