package com.dotmatt.explore

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dotmatt.explore.screens.HomeScreen
import com.dotmatt.explore.screens.LoginScreen
import com.dotmatt.explore.screens.SignupScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()

    Scaffold(bottomBar = { BottomBar(route = backstackEntry?.destination?.route) })
    {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("signup") { SignupScreen(navController) }
        }
    }
}

@Composable
fun BottomBar(route: String?) {
    if (route.equals("login") || (route.equals("signup"))) return
    Text("Bottom nav here")
}