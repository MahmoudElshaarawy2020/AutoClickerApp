package com.example.autoclickerapp.navigation.app_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.autoclickerapp.R
import com.example.autoclickerapp.navigation.screen.Screen
import com.example.autoclickerapp.navigation.bottom_navbar.BottomNavigationBar
import com.example.autoclickerapp.navigation.bottom_navbar.navbar_items.BottomNavItem
import com.example.autoclickerapp.view.user_view.LoginScreen
import com.example.autoclickerapp.view.on_boarding.OnBoardingScreen
import com.example.autoclickerapp.view.user_view.SignUpScreen
import com.example.autoclickerapp.view.user_view.UserProfileScreen
import com.example.autoclickerapp.view.user_view.HomeScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: String,
    navController: NavHostController,
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home_ic),
        BottomNavItem("Profile", R.drawable.person_ic),
    )
    val selectedItemIndex = when {
        currentRoute?.startsWith(Screen.Home.route) == true -> 0
        currentRoute?.startsWith(Screen.UserProfile.route) == true -> 1
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute == Screen.Home.route || currentRoute == Screen.UserProfile.route) {
                BottomNavigationBar(
                    items = bottomNavItems,
                    selectedItem = selectedItemIndex,
                    onItemSelected = { index ->
                        val route = when (index) {
                            0 -> Screen.Home.route
                            1 -> Screen.UserProfile.route
                            else -> Screen.Home.route
                        }
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Login.route) { LoginScreen(modifier = Modifier.fillMaxSize()) }
            composable(Screen.Home.route) { HomeScreen(modifier = Modifier.fillMaxSize()) }
            composable(Screen.OnBoarding.route) {
                OnBoardingScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    onRoleClickBoarding = { role ->
                        navController.navigate(Screen.SignUp.createRoute(role))
                    },
                    onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                )
            }
            composable(Screen.SignUp.route) { SignUpScreen(modifier = Modifier.fillMaxSize()) }
            composable(Screen.UserProfile.route) { UserProfileScreen(modifier = Modifier.fillMaxSize()) }
        }
    }
}
