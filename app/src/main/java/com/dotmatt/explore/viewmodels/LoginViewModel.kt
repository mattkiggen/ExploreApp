package com.dotmatt.explore.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dotmatt.explore.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun handleSignIn(context: Context, navController: NavController) {
        if (email.value.isEmpty() || password.value.isEmpty()) return

        viewModelScope.launch {
            userService.login(email.value, password.value) { error ->
                if (error == null) {
                    navController.navigate("home") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                } else {
                    Toast.makeText(context, "Error signing in, check your credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun handleSignup(navController: NavController) {
        navController.navigate("signup")
    }
}