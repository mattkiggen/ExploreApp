package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val landmark = viewModel.preferredLandmark.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Text(text = "Settings")
        Text("Preferred landmark: ${landmark.value}")
        Button(onClick = {
            viewModel.handleLogout {
                navController.navigate("login") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }
        }) {
            Text(text = "Logout")
        }
    }
}