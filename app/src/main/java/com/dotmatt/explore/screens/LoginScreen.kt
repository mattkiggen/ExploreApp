package com.dotmatt.explore.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    var context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        TextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(16.dp))

        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = { viewModel.handleSignIn(context, navController) }) {
            Text(text = "Login")
        }

        Text(text = "Signup here", Modifier.clickable { viewModel.handleSignup(navController) })
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    /*LoginScreen()*/
}