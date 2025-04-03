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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.autoclickerapp.R
import com.example.autoclickerapp.navigation.screen.Screen
import com.example.autoclickerapp.viewmodel.AuthState
import com.example.autoclickerapp.viewmodel.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.roundToInt

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToSignUp: () -> Unit = {},
    onLoginClick: (Int) -> Unit = {},
    navController: NavHostController,
    authViewModel: AuthViewModel
) {

    val infiniteTransition = rememberInfiniteTransition()

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val systemUiController = rememberSystemUiController()

    val authState = authViewModel.authState.observeAsState()

    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.light_green)
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }
//    LaunchedEffect(key1 = authState.value) {
//        when (authState.value) {
//            AuthState.Authenticated -> TODO()
//            is AuthState.Error -> TODO()
//            AuthState.Loading -> TODO()
//            AuthState.Unauthenticated -> TODO()
//            null -> TODO()
//        }
//    }


    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        IdentityDialog(
            onDismiss = { showDialog = false },
            onRoleClick = { role ->
                onLoginClick(role)
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
        Text(
            text = "Welcome to AutoClicker App",
            modifier = modifier
                .padding(horizontal = 8.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.first_blue),
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.size(30.dp))

        Image(
            modifier = modifier
                .padding(top = 30.dp)
                .size(width = 200.dp, height = 200.dp)
                .offset { IntOffset(0, offsetY.roundToInt()) },
            painter = painterResource(id = R.drawable.left_click),
            contentDescription = null
        )

        Spacer(modifier = Modifier.size(150.dp))

        when (authState.value) {
            AuthState.Authenticated -> {
                Button(
                    modifier = Modifier.size(width = 160.dp, height = 50.dp),
                    onClick = { navController.navigate(Screen.Home.route) },
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(colorResource(id = R.color.first_blue))
                ) {
                    Text(
                        text = "Get Started !",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        lineHeight = 25.sp
                    )
                }
            }
            AuthState.Unauthenticated, null -> {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.size(width = 160.dp, height = 50.dp),
                        onClick = { showDialog = true },
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
                        modifier = Modifier.size(width = 160.dp, height = 50.dp),
                        onClick = { onNavigateToSignUp() },
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
            else -> {}
        }

    }

}