package com.example.autoclickerapp.navigation.screen

sealed class Screen(val route: String) {
    object Login : Screen("login_screen/{role}") {
        fun chooseRole(role: Int) = "login_screen/$role"
    }
    object OnBoarding : Screen("onBoarding_screen")
    object Home : Screen("home_screen/{role}") {
        fun chooseRole(role: Int) = "home_screen/$role"
    }
    object UserProfile : Screen("user_profile_screen")
    object AdminProfile : Screen("admin_profile_screen")
    object Notification : Screen("notification_screen")
    object SignUp : Screen("signUp_screen")
}
