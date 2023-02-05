package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dotmatt.explore.viewmodels.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel) {
    val locationPermission = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)

    if (locationPermission.hasPermission) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.startingPosition, 15f)
        }

        val context = LocalContext.current

        LaunchedEffect(true) {
            viewModel.getUserLocation(context) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
            }
        }

        GoogleMap(
            properties = MapProperties(isMyLocationEnabled = true),
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .padding(bottom = 66.dp)
                .fillMaxSize()
        ) {
            MarkerInfoWindow(state = MarkerState(position = LatLng(-33.962864, 18.409834))) { marker ->
                Column {
                    Text(marker.title ?: "Default Marker Title", color = Color.Red)
                    Text(marker.snippet ?: "Default Marker Snippet", color = Color.Red)
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