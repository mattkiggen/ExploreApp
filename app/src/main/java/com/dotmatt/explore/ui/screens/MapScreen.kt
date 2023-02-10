package com.dotmatt.explore.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        LaunchedEffect(true) {
            viewModel.viewModelScope.launch {
                viewModel.getUserLocation(context) {
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 10f)
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
                CustomMarker(it)
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