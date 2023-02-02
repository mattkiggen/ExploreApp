package com.dotmatt.explore

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dotmatt.explore.screens.HomeScreen
import com.dotmatt.explore.screens.LoginScreen
import com.dotmatt.explore.screens.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppScaffold(auth: FirebaseAuth) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { Text(text = "topbar") },
        bottomBar = { Text(text = "bottom") })
    {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(auth, navController) }
            composable("login") { LoginScreen() }
            composable("signup") { SignupScreen() }
        }
    }
}