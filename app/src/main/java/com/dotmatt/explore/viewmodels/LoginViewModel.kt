package com.dotmatt.explore.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
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

    fun handleSignIn(context: Context) {
        if (_email.value.isNullOrEmpty() || _password.value.isNullOrEmpty()) return

        FirebaseAuth.getInstance().signInWithEmailAndPassword(_email.value!!, _password.value!!).addOnCompleteListener {
            if (it.isSuccessful) {
               /* navController.navigate("home")*/
            }
        }
    }

    fun handleSignup() {
        /*navController.navigate("signup")*/
    }
}