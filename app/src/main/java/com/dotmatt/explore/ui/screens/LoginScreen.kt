package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dotmatt.explore.ui.components.PasswordTextField
import com.dotmatt.explore.viewmodels.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val context = LocalContext.current

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

        PasswordTextField(value = password, onValueChange = { viewModel.onPasswordChange(it) })

        Spacer(modifier = Modifier.size(24.dp))

        Button(
            onClick = { viewModel.handleSignIn(context, navController) },
            shape = RoundedCornerShape(percent = 50),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.size(24.dp))
        Text(
            text = "Don't have an account? Click here",
            modifier = Modifier.fillMaxWidth().clickable { viewModel.handleSignup(navController) },
            textAlign = TextAlign.Center
        )
    }
}