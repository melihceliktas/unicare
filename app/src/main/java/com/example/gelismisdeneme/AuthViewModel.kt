package com.example.gelismisdeneme

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> get() = _isUserLoggedIn



    init {
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            _isUserLoggedIn.value = auth.currentUser != null
        }
    }

    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        studentNumber: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Ek kullanıcı bilgilerini Firestore'a kaydet
                        val userMap = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName,
                            "studentNumber" to studentNumber,
                            "email" to email
                        )
                        firestore.collection("users").document(userId)
                            .set(userMap)
                            .addOnSuccessListener {
                                onResult(true, "Kayıt başarılı!")
                            }
                            .addOnFailureListener { e ->
                                onResult(false, "Kayıt başarısız: ${e.message}")
                            }
                    } else {
                        onResult(false, "Kullanıcı bilgisi alınamadı.")
                    }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Giriş başarılı!")
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
}