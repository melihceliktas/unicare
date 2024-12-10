package com.example.gelismisdeneme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


/*
* users (collection)
  └── {userId} (document)
        └── journals (collection)
            └── {journalId} (document)
                └── journalPages (collection)
                    └── {pageId} (document)
                        └── content: string

*
* */

class app1viewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val names = mutableStateListOf<String>()  // Kullanıcı isimlerini tutacak liste

    // name'i mutableStateOf ile reactive state olarak tanımlıyoruz
    var name by mutableStateOf("")  // Kullanıcının input kısmında yazdığı isim

    // Firebase'den kullanıcı isimlerini almak için fonksiyon
    fun fetchUserNames() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .collection("names")
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        Log.w("Firestore", "Listen failed.", exception)
                        return@addSnapshotListener
                    }

                    names.clear()
                    snapshot?.documents?.forEach { document ->
                        val name = document.getString("name")
                        if (name != null) {
                            names.add(name)
                        }
                    }
                }
        }
    }

    // Yeni bir isim eklemek için fonksiyon
    fun add() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && name.isNotEmpty()) {
            val nameData = hashMapOf("name" to name)
            db.collection("users")
                .document(userId)
                .collection("names")
                .add(nameData)
                .addOnSuccessListener {
                    name = ""  // İsim eklendikten sonra input'u temizle
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error adding name", e)
                }
        }
    }

    // Bir ismi silmek için fonksiyon
    fun deleteName(name: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .collection("names")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener { snapshot ->
                    val document = snapshot.documents.firstOrNull()
                    document?.reference?.delete()
                        ?.addOnSuccessListener {
                            Log.d("Firestore", "DocumentSnapshot successfully deleted!")
                        }
                        ?.addOnFailureListener { e ->
                            Log.w("Firestore", "Error deleting document", e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error finding document", e)
                }
        }
    }
}