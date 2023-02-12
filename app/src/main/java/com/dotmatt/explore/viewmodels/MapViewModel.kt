package com.dotmatt.explore.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dotmatt.explore.models.Landmark
import com.dotmatt.explore.services.RouteService
import com.dotmatt.explore.services.StorageService
import com.dotmatt.explore.services.UserService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.api.Billing.BillingDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val storageService: StorageService, private val userService: UserService, private val routeService: RouteService) : ViewModel() {
    private val _landmarks = MutableStateFlow(listOf<Landmark>())

    val landmarks = _landmarks.asStateFlow()
    val startingPosition = LatLng(1.35, 103.87)

    private val _userUnit = MutableStateFlow("")
    val userUnit = _userUnit.asStateFlow()

    private val _userPosition = MutableStateFlow(startingPosition)
    val userPosition = _userPosition.asStateFlow()

    private val _showPolyline = MutableStateFlow(false)
    val showPolyline = _showPolyline.asStateFlow()

    private val _polylinePoints = MutableStateFlow("")
    val polylinePoints = _polylinePoints.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context, onSuccess: (Location) -> Unit) {
        val client = LocationServices.getFusedLocationProviderClient(context)
        client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener {
                _userPosition.value = LatLng(it.latitude, it.longitude)
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

    fun showPolyline(value: Boolean) {
        _showPolyline.value = value
    }

    fun setPolylinePoints(origin: LatLng, destination: LatLng) {
        routeService.getPoints(origin, destination) {
            _polylinePoints.value = it.routes[0].polyline.encodedPolyline
        }
    }
}