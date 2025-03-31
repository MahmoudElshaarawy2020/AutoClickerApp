package com.example.autoclickerapp.view.on_boarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.autoclickerapp.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.roundToInt

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit = {},
    onRoleClickBoarding: (Int) -> Unit = {},
    navController: NavHostController
) {

    val infiniteTransition = rememberInfiniteTransition()

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -20f, // Move up
        targetValue = 20f,   // Move down
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing), // Speed of shaking
            repeatMode = RepeatMode.Reverse
        )
    )
    val systemUiController = rememberSystemUiController()

    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.light_green)
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }


    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        IdentityDialog(
            onDismiss = { showDialog = false },
            onRoleClick = { role ->
                onRoleClickBoarding(role)
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier
                .padding(top = 30.dp)
                .size(width = 190.dp, height = 50.dp)
                .weight(0.1f)
                .offset { IntOffset(0, offsetY.roundToInt()) },
            painter = painterResource(id = R.drawable.left_click),
            contentDescription = null
        )
        Spacer(modifier = modifier.size(height = 50.dp, width = 0.dp))

        Text(
            text = "Welcome to AutoClicker App",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.first_blue),
            lineHeight = 28.sp
        )
        Spacer(modifier = modifier.size(height = 30.dp, width = 0.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.1f),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = modifier
                    .size(width = 160.dp, height = 50.dp),
                onClick = { onNavigateToLogin() },
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

            Spacer(modifier = Modifier.size(8.dp))

            Button(
                modifier = modifier
                    .size(width = 160.dp, height = 50.dp),
                onClick = { showDialog = true },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.teal_700))
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    lineHeight = 25.sp
                )
            }
        }
    }
}