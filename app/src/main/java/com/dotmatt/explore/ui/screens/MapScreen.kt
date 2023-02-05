package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen() {
    val locationPermission = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    if (locationPermission.hasPermission) {
        val singapore = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            MarkerInfoWindow(state = MarkerState(position = singapore)) { marker ->
                Column {
                    Text(marker.title ?: "Default Marker Title", color = Color.Red)
                    Text(marker.snippet ?: "Default Marker Snippet", color = Color.Red)
                }
            }
        }
    } else {
        Column {
            Text(text = "To load the map we need location permissions")
            Button(onClick = { locationPermission.launchPermissionRequest() }) {
                Text(text = "Grant permission")
            }
        }
    }
}