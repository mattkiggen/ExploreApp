package com.dotmatt.explore.services

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserService @Inject constructor(private val auth: FirebaseAuth) {
    val currentUser get() = auth.currentUser

    fun login(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    fun signup(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    fun logout() {
        auth.signOut()
    }
}