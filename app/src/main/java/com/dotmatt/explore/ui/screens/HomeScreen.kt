package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
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

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            Icons.Outlined.Bookmarks,
            contentDescription = "Favourites",
            tint = Color(0xffe5e5ea),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = "You currently have no favourites")
    }
}