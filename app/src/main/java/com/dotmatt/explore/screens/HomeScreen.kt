package com.dotmatt.explore.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()

    LaunchedEffect(Unit) {
        viewModel.viewModelScope.launch {
            if (Firebase.auth.currentUser == null) {
                navController.navigate("login")
            }
        }
    }

    Button(onClick = { viewModel.logout(navController) }) {
        Text(text = "Logout")
    }
}