package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dotmatt.explore.ui.components.PasswordTextField
import com.dotmatt.explore.viewmodels.SignupViewModel

@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()

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

        Spacer(modifier = Modifier.size(16.dp))

        PasswordTextField(value = confirmPassword, label = "Confirm Password", onValueChange = { viewModel.onConfirmPasswordChange(it) })

        Spacer(modifier = Modifier.size(24.dp))

        Button(
            onClick = { viewModel.handleSignup(navController) },
            shape = RoundedCornerShape(percent = 50),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Signup")
        }
    }
}