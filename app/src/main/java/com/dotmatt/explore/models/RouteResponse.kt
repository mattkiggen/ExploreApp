package com.dotmatt.explore.models

data class RouteResponse(val routes: List<Route>)

data class Route(
    val distanceMeters: Int,
    val duration: String,
    val polyline: Polyline
)

data class Polyline(val encodedPolyline: String)