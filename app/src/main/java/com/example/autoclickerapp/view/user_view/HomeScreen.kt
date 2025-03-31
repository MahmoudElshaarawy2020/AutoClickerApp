package com.example.autoclickerapp.view.user_view

import android.content.*
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.example.autoclickerapp.R
import com.example.autoclickerapp.notification.NotificationService
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()

    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.light_green)
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }
    var isServiceEnabled by remember { mutableStateOf(isAccessibilityServiceEnabled(context)) }

    LaunchedEffect(Unit) {
        isServiceEnabled = isAccessibilityServiceEnabled(context)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                isServiceEnabled = isAccessibilityServiceEnabled(context)

                if (!isServiceEnabled) {
                    openAccessibilitySettings(context)
                    Handler(Looper.getMainLooper()).postDelayed({
                        isServiceEnabled = isAccessibilityServiceEnabled(context)
                    }, 2000)
                } else {
                    Log.d("HomeScreen", "🚀 Navigating to home screen...")
                    navigateToHomeScreen(context)
                    Handler(Looper.getMainLooper()).postDelayed({
                        Log.d("HomeScreen", "🔔 Starting Notification Service...")
                        startNotificationService(context)
                    }, 1000) // Delay to ensure smooth transition
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.first_blue),
                contentColor = Color.White
            )
        ) {
            Text(if (isServiceEnabled) "Go to Home & Start Scrolling" else "Enable Accessibility Service")
        }
    }
}


// Function to check if Accessibility Service is enabled
fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val enabledServices = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    val colonSplitter = enabledServices.split(":")
    return colonSplitter.any { it.contains(context.packageName) }
}


// Open Accessibility settings for user to enable service
fun openAccessibilitySettings(context: Context) {
    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

// Start the NotificationService
fun startNotificationService(context: Context) {
    val intent = Intent(context, NotificationService::class.java)
    context.startForegroundService(intent)
}

// Stop the NotificationService when the app is destroyed
fun stopNotificationService(context: Context) {
    val intent = Intent(context, NotificationService::class.java)
    context.stopService(intent)
}

// Function to navigate to the home screen
fun navigateToHomeScreen(context: Context) {
    val intent = Intent(Intent.ACTION_MAIN)
    intent.addCategory(Intent.CATEGORY_HOME)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}


