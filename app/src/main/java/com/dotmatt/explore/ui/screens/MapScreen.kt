package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.dotmatt.explore.ui.components.CustomMarker
import com.dotmatt.explore.viewmodels.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel) {
    val locationPermission =
        rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)

    if (locationPermission.hasPermission) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.startingPosition, 10f)
        }

        val context = LocalContext.current
        val landmarks = viewModel.landmarks.collectAsState()
        val userUnit = viewModel.userUnit.collectAsState()
        val userPosition = viewModel.userPosition.collectAsState()

        val showPolyline = remember { mutableStateOf(false) }
        val selectedLandmarkPosition = remember { mutableStateOf(LatLng(0.0, 0.0)) }

        LaunchedEffect(true) {
            viewModel.viewModelScope.launch {
                viewModel.getUserLocation(context) {
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 10f)
                }
                viewModel.setUserUnit()
                viewModel.setLandmarks()
            }
        }

        GoogleMap(
            properties = MapProperties(isMyLocationEnabled = true),
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .padding(bottom = 66.dp)
                .fillMaxSize()
        ) {
            landmarks.value.forEach {
                CustomMarker(
                    it,
                    userPosition.value,
                    unitType = userUnit.value,
                    onClick = { marker ->
                        selectedLandmarkPosition.value = marker.position
                        showPolyline.value = true
                        true
                    },
                    onClose = { showPolyline.value = false })
            }

            if (showPolyline.value) {
                Polyline(points = listOf(userPosition.value, selectedLandmarkPosition.value))
            }
        }
    } else {
        Column {
            Text(text = "This app requires location permission to function correctly")
            Button(onClick = { locationPermission.launchPermissionRequest() }) {
                Text(text = "Grant permission")
            }
        }
    }
}