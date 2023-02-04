package com.dotmatt.explore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val email by viewModel.email.collectAsState()

    LaunchedEffect(true) {
        viewModel.viewModelScope.launch {
            if (viewModel.isSignedIn) {
                viewModel.setState()
            } else {
                navController.navigate("login")
            }
        }
    }

    Column {
        Text(text = email)
        Button(onClick = { viewModel.logout(navController) }) {
            Text(text = "Logout")
        }
    }
}