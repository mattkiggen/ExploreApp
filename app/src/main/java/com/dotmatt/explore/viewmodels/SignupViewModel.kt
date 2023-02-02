package com.dotmatt.explore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dotmatt.explore.services.UserService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")
    private val _confirmPassword = MutableLiveData("")

    val email: LiveData<String> = _email
    val password: LiveData<String> = _password
    val confirmPassword: LiveData<String> = _confirmPassword

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
        if (_email.value.isNullOrEmpty() || _password.value.isNullOrEmpty() || _confirmPassword.value.isNullOrEmpty()) return
        if (!_confirmPassword.value.equals(_password.value)) return

        userService.signup(_email.value!!, _password.value!!) { error ->
            if (error == null) {
                navController.navigate("home")
            } else {

            }
        }
    }
}