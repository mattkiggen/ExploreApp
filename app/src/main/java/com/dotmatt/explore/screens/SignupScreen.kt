package com.dotmatt.explore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dotmatt.explore.viewmodels.SignupViewModel

@Composable
fun SignupScreen() {
    val viewModel = hiltViewModel<SignupViewModel>()
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val confirmPassword by viewModel.confirmPassword.observeAsState("")

    Column {
        TextField(
            value = email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { viewModel.handleSignup() }) {
            Text("Signup")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPreview() {
    SignupScreen()
}