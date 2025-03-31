package com.example.autoclickerapp.navigation.screen

sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object OnBoarding : Screen("onBoarding_screen")
    object Home : Screen("home_screen")
    object UserProfile : Screen("user_profile_screen")
    object AdminProfile : Screen("admin_profile_screen")
    object Notification : Screen("notification_screen")
    object SignUp : Screen("signUp_screen/{role}") {
        fun createRoute(role: Int) = "signUp_screen/$role"
    }
}
