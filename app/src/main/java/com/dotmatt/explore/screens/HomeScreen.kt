package com.dotmatt.explore.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(auth: FirebaseAuth, navController: NavController) {
    if (auth.currentUser == null) {
        navController.navigate("login")
    }

    Button(onClick = { auth.signOut() }) {
        Text(text = "Logout")
    }
}