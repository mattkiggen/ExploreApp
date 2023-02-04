package com.dotmatt.explore.viewmodels

import androidx.lifecycle.ViewModel
import com.dotmatt.explore.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userService: UserService) : ViewModel() {
    private val _preferredLandmark = MutableStateFlow("")
    val preferredLandmark = _preferredLandmark.asStateFlow()

    init {
        _preferredLandmark.value = "all"
    }

    fun handleLogout(onLogout: () -> Unit) {
        userService.logout()
        onLogout()
    }
}