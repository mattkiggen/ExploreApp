package com.dotmatt.explore.ui.components

import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.NearMe
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dotmatt.explore.models.Landmark
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState

@Composable
fun CustomMarker(
    landmark: Landmark,
    currentUserLocation: LatLng,
    unitType: String,
    onClick: (Marker) -> Boolean,
    onClose: (Marker) -> Unit
) {
    val distance = remember {
        val result = FloatArray(1)

        Location.distanceBetween(
            currentUserLocation.latitude,
            currentUserLocation.longitude,
            landmark.location.latitude,
            landmark.location.longitude,
            result
        )

        val distance = if (unitType == "metric") result[0] / 1000 else result[0] / 1609.344f
        mutableStateOf(distance)
    }

    val unit = if (unitType == "metric") "km" else "miles"

    MarkerInfoWindow(
        state = MarkerState(position = landmark.location),
        title = landmark.title,
        snippet = landmark.description,
        onClick = {
            it.showInfoWindow()
            onClick(it)
        },
        onInfoWindowClose = {
            it.hideInfoWindow()
            onClose(it)
        }
    ) { marker ->
        Surface(
            elevation = 8.dp,
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(marker.title ?: "Loading", color = Color.Black, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.size(8.dp))

                Text(marker.snippet ?: "Loading", color = Color.Black, fontSize = 12.sp)

                Spacer(modifier = Modifier.size(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.PushPin,
                            contentDescription = "Landmark type",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(landmark.type, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.NearMe,
                            contentDescription = "Distance",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(text = "~ ${distance.value} $unit", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}