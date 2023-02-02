package com.dotmatt.explore.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.dotmatt.explore.R
import com.dotmatt.explore.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _email = MutableLiveData("")
    private val _password = MutableLiveData("")

    val email: LiveData<String> = _email
    val password: LiveData<String> = _password

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun handleSignIn(context: Context, navController: NavController) {
        if (_email.value.isNullOrEmpty() || _password.value.isNullOrEmpty()) return

        viewModelScope.launch {
            userService.login(email.value!!, password.value!!) { error ->
                if (error == null) {
                    navController.navigate("home")
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