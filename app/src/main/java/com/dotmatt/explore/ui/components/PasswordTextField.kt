package com.dotmatt.explore.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit) {
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Password") },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(image, description)
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}
