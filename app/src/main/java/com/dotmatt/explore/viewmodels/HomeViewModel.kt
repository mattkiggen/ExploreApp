package com.dotmatt.explore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dotmatt.explore.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email
    val isSignedIn = userService.currentUser != null

    fun setState() {
        _email.value = userService.currentUser?.email
    }

    fun logout(navController: NavController) {
        userService.logout()
        navController.navigate("login")
    }
}