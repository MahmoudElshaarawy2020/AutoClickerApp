package com.example.autoclickerapp.view.user_view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.autoclickerapp.R
import com.example.autoclickerapp.data.DataStoreManager
import com.example.autoclickerapp.model.utils.UiState
import com.example.autoclickerapp.navigation.screen.Screen
import com.example.autoclickerapp.view.utils.CustomTextField
import com.example.autoclickerapp.viewmodel.AuthState
import com.example.autoclickerapp.viewmodel.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToOnBoarding: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel(),
    dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current)
) {
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.light_green)

    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Collect UI state from ViewModel
    val authState = viewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated ->{
                dataStoreManager.saveUserData(userName, email, phoneNumber)
                navController.navigate(Screen.Home.route)
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }

    LazyColumn(
        modifier = Modifier
            .background(color = backgroundColor)
            .padding(vertical = 32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Create Your Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.first_blue),
                lineHeight = 28.sp
            )
            Text(
                text = "Get Your Scroll Easy",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.second_blue),
                lineHeight = 25.sp
            )
        }

        // User Name Field
        item {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 17.dp),
                text = "User Name",
                fontSize = 17.sp,
                lineHeight = 21.sp,
                color = colorResource(id = R.color.first_blue),
                fontWeight = FontWeight.Medium
            )

            CustomTextField(
                value = userName,
                onValueChange = { userName = it },
                label = "Your Name",
                focusedBorderColor = colorResource(id = R.color.second_blue),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        // Email Field
        item {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 17.dp),
                text = "Email",
                fontSize = 17.sp,
                lineHeight = 21.sp,
                color = colorResource(id = R.color.first_blue),
                fontWeight = FontWeight.Medium
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Your Email",
                isEmail = true,
                focusedBorderColor = colorResource(id = R.color.second_blue),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        // Phone Number Field
        item {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 17.dp),
                text = "Phone Number",
                fontSize = 17.sp,
                lineHeight = 21.sp,
                color = colorResource(id = R.color.first_blue),
                fontWeight = FontWeight.Medium
            )

            CustomTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                isPhoneNumber = true,
                label = "Your Phone Number",
                focusedBorderColor = colorResource(id = R.color.second_blue),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        // Password Field
        item {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 17.dp),
                text = "Password",
                fontSize = 17.sp,
                lineHeight = 21.sp,
                color = colorResource(id = R.color.first_blue),
                fontWeight = FontWeight.Medium
            )

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                isPassword = true,
                isEncrypted = true,
                label = "Your Password",
                focusedBorderColor = colorResource(id = R.color.second_blue),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        // Confirm Password Field
        item {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, bottom = 8.dp, top = 17.dp),
                text = "Confirm Password",
                fontSize = 17.sp,
                lineHeight = 21.sp,
                color = colorResource(id = R.color.first_blue),
                fontWeight = FontWeight.Medium
            )

            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                isEncrypted = true,
                label = "Confirm Password",
                focusedBorderColor = colorResource(id = R.color.second_blue),
                unfocusedBorderColor = colorResource(id = R.color.light_grey),
                cursorColor = colorResource(id = R.color.light_grey),
                isFocused = false
            )
        }

        // Sign Up Button
        item {
            Spacer(modifier = modifier.size(height = 24.dp, width = 0.dp))
            Button(
                modifier = modifier
                    .padding(horizontal = 80.dp)
                    .height(50.dp)
                    .width(150.dp),
                onClick = {
                    if (
                        email.isNotBlank() &&
                        password.isNotBlank() &&
                        confirmPassword.isNotBlank() &&
                        userName.isNotBlank() &&
                                phoneNumber.isNotBlank()
                    ) {
                        viewModel.signUp(email, password)
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill all fields",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.first_blue))
            ) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.white),
                        lineHeight = 25.sp
                    )

            }

            Spacer(modifier = modifier.size(height = 8.dp, width = 0.dp))
            Text(
                modifier = modifier.clickable {
                    onNavigateToOnBoarding()
                },
                text = "Proceed to login",
                textDecoration = TextDecoration.Underline,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.first_blue),
            )
            Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))
        }
    }
}