package com.example.gelismisdeneme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = auth.currentUser?.uid

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> get() = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> get() = _lastName

    private val _studentNumber = MutableStateFlow("")
    val studentNumber: StateFlow<String> get() = _studentNumber

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        loadUserData()
    }

    private fun loadUserData() {
        userId?.let {
            firestore.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        _firstName.value = document.getString("firstName") ?: ""
                        _lastName.value = document.getString("lastName") ?: ""
                        _studentNumber.value = document.getString("studentNumber") ?: ""
                        _email.value = document.getString("email") ?: ""
                    } else {
                        _errorMessage.value = "Kullanıcı verileri bulunamadı."
                    }
                }
                .addOnFailureListener { e ->
                    _errorMessage.value = "Hata: ${e.message}"
                }
        }
    }

    fun updateFirstName(newFirstName: String) {
        _firstName.value = newFirstName
    }

    fun updateLastName(newLastName: String) {
        _lastName.value = newLastName
    }

    fun updateStudentNumber(newStudentNumber: String) {
        _studentNumber.value = newStudentNumber
    }

    fun saveUserData() {
        userId?.let {
            val updatedData = mapOf(
                "firstName" to _firstName.value,
                "lastName" to _lastName.value,
                "studentNumber" to _studentNumber.value
            )
            firestore.collection("users").document(it)
                .update(updatedData)
                .addOnSuccessListener {
                    _errorMessage.value = "Bilgiler güncellendi!"
                }
                .addOnFailureListener { e ->
                    _errorMessage.value = "Güncelleme başarısız: ${e.message}"
                }
        }
    }
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid // Kullanıcı oturumu açmışsa userId'yi döndürür
    }
}
