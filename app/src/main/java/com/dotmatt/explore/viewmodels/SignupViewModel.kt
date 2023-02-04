package com.dotmatt.explore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dotmatt.explore.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onConfirmPasswordChange(value: String) {
        _confirmPassword.value = value
    }

    fun handleSignup(navController: NavController) {
        if (email.value.isEmpty() || password.value.isEmpty() || confirmPassword.value.isEmpty()) return
        if (confirmPassword.value != password.value) return

        userService.signup(email.value, password.value) { error ->
            if (error == null) {
                navController.navigate("home")
            } else {

            }
        }
    }
}