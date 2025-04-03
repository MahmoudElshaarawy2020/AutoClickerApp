package com.example.autoclickerapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.example.autoclickerapp.navigation.screen.Screen
import com.example.autoclickerapp.navigation.app_navigation.AppNavigation
import com.example.autoclickerapp.view.on_boarding.OnBoardingScreen
import com.example.autoclickerapp.view.ui.theme.AutoClickerAppTheme
import com.example.autoclickerapp.view.user_view.stopNotificationService
import com.example.autoclickerapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        setContent {
            AutoClickerAppTheme {
                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf(Screen.OnBoarding.route) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(startDestination, navController, authViewModel)
                }
            }
        }
        val auth = FirebaseAuth.getInstance()
        Log.d("FirebaseAuth", "Firebase Initialized: ${auth.currentUser?.uid ?: "No user"}")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopNotificationService(this)
    }
}


