package com.dotmatt.explore.models

data class RouteRequest(
    val origin: Waypoint,
    val destination: Waypoint,
    val polylineQuality: String
)

data class Waypoint(
    val location: Location
)

data class Location(
    val latLng: LatLng
)

data class LatLng(
    val latitude: Double,
    val longitude: Double
)