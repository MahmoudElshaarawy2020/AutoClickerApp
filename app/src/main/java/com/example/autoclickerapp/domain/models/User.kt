package com.example.autoclickerapp.domain.models

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val gender: String = "",
    val age: Int = 0,
    val userType: String = ""
)
