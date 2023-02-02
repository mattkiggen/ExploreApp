package com.dotmatt.explore.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class UserService {
    private val _isSignedIn = MutableLiveData(false)
    private val _auth = FirebaseAuth.getInstance()

    val isSignedIn: LiveData<Boolean> = _isSignedIn // could make it the current user too

    fun onAuthChange(value: Boolean) {
        _isSignedIn.value = value
    }

    fun handleLogin(email: String, password: String) {
        _auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) _isSignedIn.value = true
        }
    }

    fun handleSignup(email: String, password: String) {
        _auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

        }
    }

    fun handleLogout() {
        _auth.signOut()
        _isSignedIn.value = false
    }
}