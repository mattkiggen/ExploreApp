package com.dotmatt.explore

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dotmatt.explore.ui.components.BottomNav
import com.dotmatt.explore.ui.screens.*

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    Scaffold(bottomBar = { BottomBar(currentRoute = currentRoute, navController) })
    {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("signup") { SignupScreen(navController) }
            composable("settings") { SettingsScreen() }
            composable("map") { MapScreen() }
        }
    }
}

@Composable
fun BottomBar(currentRoute: String?, navController: NavController) {
    if (currentRoute.equals("login") || (currentRoute.equals("signup"))) return
    BottomNav(currentRoute = currentRoute, navController = navController)
}