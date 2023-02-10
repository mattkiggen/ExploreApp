package com.dotmatt.explore.services

import com.dotmatt.explore.models.Landmark
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class StorageService @Inject constructor(private val firestore: FirebaseFirestore) {
    fun initUserPreferences(uid: String) {
        val settings = hashMapOf(
            "preferredLandmark" to "all",
            "unit" to "metric",
            "favourites" to listOf<String>()
        )

        firestore.collection("users").document(uid).set(settings)
    }

    fun getUserPreferences(uid: String, onSuccess: (DocumentSnapshot) -> Unit) {
        val doc = firestore.collection("users").document(uid)
        doc.get().addOnSuccessListener { onSuccess(it) }
    }

    fun updateUserPreferences(uid: String, settings: HashMap<String, String>) {
        firestore.collection("users").document(uid).set(settings, SetOptions.merge())
    }

    fun getLandmarks(onSuccess: (List<Landmark>) -> Unit) {
        val collection = firestore.collection("landmarks")

        collection.get().addOnSuccessListener { query ->
            val landmarks = mutableListOf<Landmark>()

            query.documents.forEach { doc ->
                val title = doc.get("title") as String
                val description = doc.get("description") as String
                val lat = doc.get("lat") as Double
                val lng = doc.get("lng") as Double
                val type = doc.get("type") as String

                val landmark = Landmark(title, description, LatLng(lat, lng), type)

                landmarks.add(landmark)
            }

            onSuccess(landmarks)
        }
    }
}