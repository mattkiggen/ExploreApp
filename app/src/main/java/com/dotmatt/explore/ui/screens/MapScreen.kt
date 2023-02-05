package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
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
            position = CameraPosition.fromLatLngZoom(viewModel.startingPosition, 15f)
        }

        val context = LocalContext.current
        val landmarks = viewModel.landmarks.collectAsState()

        LaunchedEffect(true) {
            viewModel.viewModelScope.launch {
                viewModel.getUserLocation(context) {
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                }
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
                MarkerInfoWindow(
                    state = MarkerState(position = it.location),
                    title = it.title,
                    snippet = it.description
                ) { marker ->
                    Surface(elevation = 8.dp) {
                        Column {
                            Text(marker.title ?: "Loading", color = Color.Red)
                            Text(marker.snippet ?: "Loading", color = Color.Red)
                        }
                    }
                }
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