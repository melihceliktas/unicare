package com.example.gelismisdeneme


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/*journals (koleksiyon)
  |
  ----> journalId (belge)
          |
          ----> title: "Günlük Başlığı"
          ----> content: "Günlük içeriği"
          ----> createdAt: "Timestamp"
          ----> userId: "user_id" (giriş yapan kullanıcının ID'si)

 */


data class JournalEntry(
    var id: String = "", // Firestore belge ID'si
    val title: String = "",
    val content: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val userId: String = "" // Giriş yapan kullanıcının ID'si
)

class JournalViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    // Günlükleri almak için bir fonksiyon
    fun getJournalEntries(userId: String, onResult: (List<JournalEntry>) -> Unit) {
        db.collection("journals")
            .whereEqualTo("userId", userId) // userId ile filtreleme
            .orderBy("createdAt", Query.Direction.DESCENDING) // Günlükleri zaman sırasına göre sıralıyoruz
            .get()
            .addOnSuccessListener { documents ->
                val journals = mutableListOf<JournalEntry>()
                for (document in documents) {
                    val journal = document.toObject(JournalEntry::class.java)
                    journal.id = document.id // ID'yi ekliyoruz
                    journals.add(journal)
                }
                onResult(journals)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

    // Yeni bir günlük eklemek için bir fonksiyon
    fun addJournalEntry(title: String, content: String, onComplete: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val journalEntry = JournalEntry(title = title, content = content, userId = userId)

            FirebaseFirestore.getInstance().collection("journals")
                .add(journalEntry)
                .addOnSuccessListener {
                    Log.d("JournalViewModel", "Günlük başarıyla eklendi!")
                    onComplete(true) // Başarılı işlemi bildir
                }
                .addOnFailureListener { e ->
                    Log.e("JournalViewModel", "Günlük eklenemedi: ${e.message}")
                    onComplete(false) // Hata durumunda false döndür
                }
        } else {
            Log.e("JournalViewModel", "Kullanıcı girişi yapılmadı!")
            onComplete(false)
        }
    }

    fun deleteJournalEntry(journalId: String,userId: String, onComplete: (Boolean) -> Unit) {
        db.collection("journals")
            .document(journalId)
            .delete()
            .addOnSuccessListener {
                Log.d("JournalViewModel", "Günlük başarıyla silindi!")

                getJournalEntries(userId) { updatedJournals ->
                    // Güncel veriyi UI'ya yolla
                    onComplete(true)
                }

            }
            .addOnFailureListener { e ->
                Log.e("JournalViewModel", "Günlük silinemedi: ${e.message}")
                onComplete(false)
            }
    }

    fun updateJournalEntry(journalId: String, newTitle: String, newContent: String, onComplete:  (Boolean) -> Unit) {
        val updatedData = mapOf(
            "title" to newTitle,
            "content" to newContent
        )

        db.collection("journals")
            .document(journalId)
            .update(updatedData)
            .addOnSuccessListener {
                Log.d("JournalViewModel", "Günlük başarıyla güncellendi!")
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("JournalViewModel", "Günlük güncellenemedi: ${e.message}")
                onComplete(false)
            }
    }


}




