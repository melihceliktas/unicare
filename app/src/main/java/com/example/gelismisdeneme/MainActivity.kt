package com.example.gelismisdeneme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.gelismisdeneme.ui.theme.appTypography

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MaterialTheme(
                typography = appTypography,
                content = {

                    // App içeriği
                    AppNavigation()

                }
            )

        }
    }
}





