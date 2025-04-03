package com.example.autoclickerapp.view.user_view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.autoclickerapp.R
import com.example.autoclickerapp.model.utils.UiState
import com.example.autoclickerapp.view.utils.CustomTextField
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import android.widget.Toast
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewModelScope
import com.example.autoclickerapp.domain.models.User
import com.example.autoclickerapp.navigation.screen.Screen
import com.example.autoclickerapp.viewmodel.AuthState
import com.example.autoclickerapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    role: Int,
    navController: NavHostController,
    onNavigateToOnBoarding: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.light_green)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState = viewModel.authState.observeAsState()

    systemUiController.setStatusBarColor(
        color = backgroundColor,
        darkIcons = true
    )


    LaunchedEffect(key1 = authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate(Screen.Home.route)
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()

            }
            AuthState.Loading -> {
                // Show loading indicator
            }
            AuthState.Unauthenticated -> {

            }
            null -> {

            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Text(
            text = "Unlock Your Scrolling!",
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.first_blue),
            lineHeight = 28.sp
        )

        Text(
            text = buildAnnotatedString {
                append("Login As ")
                withStyle(
                    style = SpanStyle(
                        color = if (role == 1) colorResource(R.color.teal_200)
                        else colorResource(R.color.first_blue),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(if (role == 1) "Admin" else "User")
                }
            },
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.second_blue),
            lineHeight = 25.sp
        )

        // Email Field
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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

        // Password Field
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp, top = 17.dp),
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

        Spacer(modifier = modifier.size(height = 130.dp, width = 0.dp))

        // Login Button
        Button(
            modifier = modifier.size(width = 230.dp, height = 50.dp),
            onClick = {
                if (
                    email.isNotBlank() &&
                    password.isNotBlank()
                ) {
                    viewModel.login(
                        email = email,
                        password = password
                    )
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.first_blue))
        ) {
            Text(
                text = "Login",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center,
                color = Color.White,
                lineHeight = 25.sp
            )
        }

        Spacer(modifier = modifier.size(height = 16.dp, width = 0.dp))

        // Sign Up Navigation
        Text(
            modifier = modifier.clickable { onNavigateToOnBoarding() },
            text = "Want To Create Your Account",
            textDecoration = TextDecoration.Underline,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.first_blue),
        )

        Spacer(modifier = modifier.size(height = 45.dp, width = 0.dp))
    }
}