package com.dotmatt.explore.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class StorageService @Inject constructor(private val firestore: FirebaseFirestore) {
    fun initUserPreferences(uid: String) {
        val settings = hashMapOf(
            "preferredLandmark" to "all"
        )

        firestore.collection("users").document(uid).set(settings)
    }

    fun getUserPreferrences(uid: String) {
        val doc = firestore.collection("users").document(uid)
        doc.get().addOnSuccessListener { document ->
            if (document != null) Log.d("document", document.toString())
        }
    }
}