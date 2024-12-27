package com.example.gelismisdeneme

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.messaging.FirebaseMessaging

class SettingsViewModel : ViewModel() {

    private val _isNotificationsEnabled = MutableLiveData<Boolean>(true)
    val isNotificationsEnabled: LiveData<Boolean> get() = _isNotificationsEnabled

    private val requestPermissionLauncher =
        ActivityResultContracts.RequestPermission()

    fun onNotificationToggle(isEnabled: Boolean, context: Context) {
        if (isEnabled) {
            requestForNotifications(context)
        } else {
            // Handle disabling notifications
            Log.d("NotificationToggle", "Notifications turned off")
        }

        // Update the UI state (for the switch)
        _isNotificationsEnabled.value = isEnabled
    }

    private fun requestForNotifications(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchFCMToken()
            } else {
                ActivityCompat.requestPermissions(
                    context as Activity, // cast to activity context
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        } else {
            fetchFCMToken()
        }
    }

    private fun fetchFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FirebaseLogs", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("FirebaseLogs", "FCM registration token: $token")
        }
    }
}
