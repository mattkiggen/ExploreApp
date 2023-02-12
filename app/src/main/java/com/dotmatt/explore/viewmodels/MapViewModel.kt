package com.dotmatt.explore.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import com.dotmatt.explore.models.Landmark
import com.dotmatt.explore.services.StorageService
import com.dotmatt.explore.services.UserService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val storageService: StorageService, private val userService: UserService) : ViewModel() {
    private val _landmarks = MutableStateFlow(listOf<Landmark>())

    val landmarks = _landmarks.asStateFlow()
    val startingPosition = LatLng(1.35, 103.87)

    private val _userUnit = MutableStateFlow("")
    val userUnit = _userUnit.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context, onSuccess: (Location) -> Unit) {
        val client = LocationServices.getFusedLocationProviderClient(context)
        client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener {
                onSuccess(it)
            }
    }

    fun setUserUnit() {
        storageService.getUserPreferences(userService.currentUser?.uid!!, onSuccess = {
            val unit = it.get("unit") as String
            _userUnit.value = unit
        })
    }

    fun setLandmarks() {
        storageService.getLandmarks(onSuccess = { landmarks ->
            storageService.getUserPreferences(userService.currentUser?.uid!!,  onSuccess = {
                val preferredLandmark = it.get("preferredLandmark") as String

                if (preferredLandmark == "all")
                    _landmarks.value = landmarks
                else
                    _landmarks.value = landmarks.filter { landmark -> landmark.type == preferredLandmark }
            })
        })
    }
}