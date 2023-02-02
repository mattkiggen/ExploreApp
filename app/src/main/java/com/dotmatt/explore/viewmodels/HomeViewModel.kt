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
    fun logout(navController: NavController) {
        userService.logout()
        navController.navigate("login")
    }
}