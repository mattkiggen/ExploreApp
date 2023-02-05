package com.dotmatt.explore

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dotmatt.explore.ui.components.BottomNav
import com.dotmatt.explore.ui.screens.*
import com.dotmatt.explore.viewmodels.HomeViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    Scaffold(bottomBar = { BottomBar(currentRoute, navController) })
    {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController, hiltViewModel()) }
            composable("login") { LoginScreen(navController, hiltViewModel()) }
            composable("signup") { SignupScreen(navController, hiltViewModel()) }
            composable("settings") { SettingsScreen(navController, hiltViewModel()) }
            composable("map") { MapScreen(hiltViewModel()) }
        }
    }
}

@Composable
fun BottomBar(currentRoute: String?, navController: NavController) {
    if (currentRoute.equals("login") || (currentRoute.equals("signup"))) return
    BottomNav(currentRoute = currentRoute, navController = navController)
}