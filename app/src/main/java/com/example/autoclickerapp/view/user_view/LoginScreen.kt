package com.example.autoclickerapp.view.user_view

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.text.font.FontSynthesis.Companion.Style
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.autoclickerapp.R
import com.example.autoclickerapp.view.utils.CustomTextField
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    role: Int,
    onLoginClick: (Int) -> Unit = {},
    onNavigateToOnBoarding: () -> Unit = {},
    navController: NavHostController
) {

    val systemUiController = rememberSystemUiController()

    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.light_green)
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Text(
            text = "Unlock Your Scrolling !",
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
                        color = if (role == 1) colorResource(R.color.teal_200) else colorResource(
                            R.color.first_blue
                        ),
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
        Button(
            modifier = modifier
                .size(width = 230.dp, height = 50.dp),
            onClick = { onLoginClick(role) },
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.first_blue))
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
        Text(
            modifier = modifier.clickable {
                onNavigateToOnBoarding()
            },
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