package com.dotmatt.explore.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dotmatt.explore.services.StorageService
import com.dotmatt.explore.services.UserService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userService: UserService, private val storageService: StorageService) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    val isSignedIn = userService.currentUser != null

    fun setState() {
        if (userService.currentUser != null) {
            _email.value = userService.currentUser!!.email!!
            storageService.getUserPreferrences(userService.currentUser!!.uid)
        }
    }

    fun logout(navController: NavController) {
        userService.logout()
        navController.navigate("login")
    }
}