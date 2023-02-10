package com.dotmatt.explore.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dotmatt.explore.models.Landmark
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState

@Composable
fun CustomMarker(landmark: Landmark) {
    MarkerInfoWindow(
        state = MarkerState(position = landmark.location),
        title = landmark.title,
        snippet = landmark.description
    ) { marker ->
        Surface(elevation = 8.dp, modifier = Modifier.width(200.dp), shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(marker.title ?: "Loading", color = Color.Black, fontWeight = FontWeight.Bold)
                Text(marker.snippet ?: "Loading", color = Color.Black, fontSize = 12.sp)
                Text("Type: ${landmark.type}", color = Color.Black, fontSize = 12.sp)
            }
        }
    }
}