package com.example.gelismisdeneme



import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun JournalScreen(
    viewModel: JournalViewModel,
    //userId: String,
    navController: NavController
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user_id"




    var journalEntries by remember { mutableStateOf<List<JournalEntry>>(emptyList()) }

    // Journal verilerini almak
    LaunchedEffect(userId) {
        viewModel.getJournalEntries(userId) { entries ->
            Log.d("JournalScreen", "Alınan günlükler: ${entries}")
            journalEntries = entries
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addJournal") },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Günlük Ekle")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)

        ) {
            Text(
                "Günlükler",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 60.dp).padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary

            )

            Spacer(modifier = Modifier.height(0.dp))

            LazyColumn {
                items(journalEntries) { entry ->
                    JournalListItem(entry = entry, viewModel = viewModel, navController = navController, onDeleteSuccess = {
                        // Silme sonrası, güncellenmiş günlükleri tekrar al
                        viewModel.getJournalEntries(userId) { updatedJournals ->
                            journalEntries = updatedJournals
                        }
                    })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun JournalListItem(
    entry: JournalEntry,
    viewModel: JournalViewModel,
    navController: NavController,
    onDeleteSuccess: () -> Unit // Silme sonrası başarılı olduğunda çalışacak callback
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("editJournal/${entry.id}/${entry.title}/${entry.content}")
            },
        shape = RoundedCornerShape(12.dp), // Köşeleri yuvarlamak için

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),


    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(entry.title, style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)

            Spacer(modifier = Modifier.height(8.dp))

            Text(entry.content, style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.background)



            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Sağ ve sol hizalama
            ) {
                // Sol tarafta boşluk bırakıyoruz
                Spacer(modifier = Modifier.weight(1f))

                // Tarih sağ köşede
                Text(
                    text = "${formatDate(entry.createdAt)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .align(Alignment.Top) // Sağ üst köşeye hizala
                        .padding(top = 8.dp) // Üstten biraz boşluk
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Düzenle butonu
                OutlinedButton(
                    onClick = {
                        navController.navigate("editJournal/${entry.id}/${entry.title}/${entry.content}")
                    },
                    modifier = Modifier.padding(end = 8.dp),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.background)
                ) {
                    Text("Düzenle", color = MaterialTheme.colorScheme.background)
                }

                // Silme butonu
                Button(
                    onClick = {
                        viewModel.deleteJournalEntry(entry.id, entry.userId) { success ->
                            if (success) {
                                // Silme başarılıysa, callback'i çağır
                                onDeleteSuccess()
                                Toast.makeText(context, "Günlük silindi", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Silme başarısız", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.background),
                    colors = ButtonColors(containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContentColor =MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background)

                ) {
                    Text("Sil", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}




