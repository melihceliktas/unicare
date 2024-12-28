package com.example.gelismisdeneme
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth


@Composable

fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser // Firebase'den mevcut kullanıcı bilgisi



    val journalViewModel : JournalViewModel = viewModel()

    // Kullanıcı durumunu kontrol et
    val startDestination = if (currentUser == null) "login" else "home"

    NavHost(
        navController = navController,
        startDestination = startDestination // Dinamik başlangıç ekranı
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true } // Login ekranını geri yığından kaldır
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(onRegisterSuccess = {
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true } // Register ekranını geri yığından kaldır
                }
            })
        }

        composable("home") {
            HomeScreen(navController)
        }

        composable("details/1") {
            //NameList()
            //val userId = currentUser?.uid ?: "unknown_user_id"
            JournalScreen(viewModel = viewModel(),navController) //userId = userId, )
        }

        composable("addJournal") {
            AddJournalScreen(viewModel = viewModel(), onSaveSuccess = {
                navController.popBackStack()
            })
        }

        composable("editJournal/{journalId}/{title}/{content}") { backStackEntry ->
            val journalId = backStackEntry.arguments?.getString("journalId") ?: return@composable
            val title = backStackEntry.arguments?.getString("title") ?: return@composable
            val content = backStackEntry.arguments?.getString("content") ?: return@composable

            EditJournalScreen(
                viewModel = viewModel(),
                journalId = journalId,
                currentTitle = title,
                currentContent = content,
                navController = navController
            )
        }


        composable("videos") {
            VideosScreen(navController)
        }
        composable("details/3") { 
            MeditationScreen()
        }
        composable("details/4") { 
            AppointmentScreen()
        }
        composable("details/5") { EmptyPage() }
        composable("details/6") {
            ProfilePage(onSignOut = {

                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                // Home ekranını geri yığından kaldır
                }
            })
        }
    }
}


