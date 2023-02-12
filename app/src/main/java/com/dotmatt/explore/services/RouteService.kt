package com.dotmatt.explore.services

import android.util.Log
import com.dotmatt.explore.models.*
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RouteService @Inject constructor() {
    fun getPoints(origin: LatLng, destination: LatLng, onSuccess: (RouteResponse) -> Unit) {
        val instance = RetrofitHelper.getInstance().create(RoutesApi::class.java)

        val call = instance.GetEncodedPolyline(
            RouteRequest(
                origin = Waypoint(
                    Location(
                        com.dotmatt.explore.models.LatLng(
                            origin.latitude,
                            origin.longitude
                        )
                    )
                ),
                destination = Waypoint(
                    Location(
                        com.dotmatt.explore.models.LatLng(
                            destination.latitude,
                            destination.longitude
                        )
                    )
                ),
                polylineQuality = "OVERVIEW"
            )
        )

        call.enqueue(object : Callback<RouteResponse> {
            override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {

                Log.d("RESPONSE", response.code().toString())

                if (response.isSuccessful) {
                    val responseData = response.body()
                    onSuccess(responseData!!)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}