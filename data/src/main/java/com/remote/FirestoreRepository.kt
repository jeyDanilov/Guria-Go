package com.remote

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor() {
    private val firestore = Firebase.firestore

    suspend fun testConnection() {
        firestore
            .collection("test")
            .document("hello")
            .set(mapOf("message" to "Hello Firestore"))
            .await()
    }
}