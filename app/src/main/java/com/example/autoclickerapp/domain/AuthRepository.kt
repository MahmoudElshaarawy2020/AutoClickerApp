package com.example.autoclickerapp.domain

import com.example.autoclickerapp.domain.models.User
import com.google.firebase.auth.FirebaseUser
interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<FirebaseUser?>
    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        gender: String,
        age: Int,
        userType: String
    ): Result<FirebaseUser?>
    suspend fun signOut(): Result<Unit>
    fun getCurrentUser(): FirebaseUser?
    suspend fun addUserToFirestore(user: User): Result<Unit>

    // Fetch user from Firestore
    suspend fun getUserFromFirestore(userId: String): Result<User>
}