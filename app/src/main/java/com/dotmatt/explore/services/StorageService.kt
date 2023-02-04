package com.dotmatt.explore.services

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class StorageService @Inject constructor(private val firestore: FirebaseFirestore) {
    fun initUserPreferences(uid: String) {
        val settings = hashMapOf(
            "preferredLandmark" to "all",
            "unit" to "metric"
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

    fun getLandmarks() {
        val collection = firestore.collection("landmarks")
        collection.get().addOnSuccessListener { it.documents.toList() }
    }
}