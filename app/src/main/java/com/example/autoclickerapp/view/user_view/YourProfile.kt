package com.example.autoclickerapp.view.user_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autoclickerapp.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun UserProfileScreen(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(R.color.first_blue)
    var name by remember { mutableStateOf("Mahmoud") }
    var email by remember { mutableStateOf("mahmoud34@gmail.com") }
    var phoneNumber by remember { mutableStateOf("+201018490780") }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = true
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.white))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Your Profile",
                modifier = Modifier.padding(vertical = 16.dp),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.user),
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp)
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))



            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.first_blue)
            )

            Spacer(modifier = Modifier.height(20.dp))
            ProfileInfoItem(label = "Email Address", value = email)
            ProfileInfoItem(label = "Phone Number", value = phoneNumber)

            Spacer(modifier = Modifier.height(40.dp))

        }
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
        }
        Text(text = value,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            fontSize = 14.sp, color = Color.Gray)
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}