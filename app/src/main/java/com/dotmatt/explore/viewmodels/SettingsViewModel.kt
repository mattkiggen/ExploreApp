package com.dotmatt.explore.viewmodels

import androidx.lifecycle.ViewModel
import com.dotmatt.explore.services.StorageService
import com.dotmatt.explore.services.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val userService: UserService, private val storageService: StorageService) : ViewModel() {
    private val _preferredLandmark = MutableStateFlow("")
    private val _unitOfMeasurement = MutableStateFlow("")
    private val uid = userService.currentUser?.uid

    val unitOfMeasurement = _unitOfMeasurement.asStateFlow()
    val preferredLandmark = _preferredLandmark.asStateFlow()


    init {
        storageService.getUserPreferences(uid!!) { doc ->
            if (doc.get("preferredLandmark") != null) _preferredLandmark.value = doc.get("preferredLandmark") as String
            if (doc.get("unit") != null) _unitOfMeasurement.value = doc.get("unit") as String
        }
    }

    fun landmarkChange(type: String) {
        _preferredLandmark.value = type
        val settings = hashMapOf("preferredLandmark" to type)
        storageService.updateUserPreferences(uid!!, settings)
    }

    fun measurementChange(type: String) {
        _unitOfMeasurement.value = type
        val settings = hashMapOf("unit" to type)
        storageService.updateUserPreferences(uid!!, settings)
    }

    fun handleLogout(onLogout: () -> Unit) {
        userService.logout()
        onLogout()
    }
}