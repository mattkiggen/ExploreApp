package com.dotmatt.explore.services

import com.dotmatt.explore.models.RouteRequest
import com.dotmatt.explore.models.RouteResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RoutesApi {
    @Headers(
        "Content-Type: application/json",
        "X-Goog-Api-Key: ",
        "X-Goog-FieldMask: routes.distanceMeters,routes.duration,routes.polyline.encodedPolyline"
    )
    @POST("/directions/v2:computeRoutes")
    fun GetEncodedPolyline(@Body body: RouteRequest): Call<RouteResponse>
}

object RetrofitHelper {
    val baseUrl = "https://routes.googleapis.com"

    fun getInstance(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}