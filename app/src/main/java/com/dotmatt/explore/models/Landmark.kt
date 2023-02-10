package com.dotmatt.explore.models

import com.google.android.gms.maps.model.LatLng

data class Landmark(val title: String, val description: String, val location: LatLng, val type: String)
