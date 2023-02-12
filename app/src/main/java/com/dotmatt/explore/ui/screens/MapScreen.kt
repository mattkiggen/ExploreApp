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
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(viewModel: MapViewModel) {
    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.INTERNET
        )
    )

    if (permissions.allPermissionsGranted) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(viewModel.startingPosition, 10f)
        }

        val context = LocalContext.current
        val landmarks = viewModel.landmarks.collectAsState()
        val userUnit = viewModel.userUnit.collectAsState()
        val userPosition = viewModel.userPosition.collectAsState()
        val showPolyline = viewModel.showPolyline.collectAsState()
        val polylinePoints = viewModel.polylinePoints.collectAsState()

        LaunchedEffect(true) {
            viewModel.viewModelScope.launch {
                viewModel.getUserLocation(context) {
                    cameraPositionState.position =
                        CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 14f)
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
                        viewModel.setPolylinePoints(userPosition.value, marker.position)
                        viewModel.showPolyline(true)
                        true
                    },
                    onClose = { viewModel.showPolyline(false) })
            }

            if (showPolyline.value) Polyline(points = PolyUtil.decode(polylinePoints.value))
        }
    } else {
        Column {
            Text(text = "This app requires location and internet permission to function correctly")
            Button(onClick = { permissions.launchMultiplePermissionRequest() }) {
                Text(text = "Grant permission")
            }
        }
    }
}