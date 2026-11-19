package com.remote

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor() {
    private val firestore = Firebase.firestore

    suspend fun testConnection() {
        try {
            firestore
                .collection("test")
                .document("hello")
                .set(mapOf("message" to "Hello Firestore"))
                .await()

            Log.d("FirestoreTest", "SUCCESS")
        } catch (e: Exception) {
            Log.e("FirestoreTest", "FAIL", e)
        }
    }
}