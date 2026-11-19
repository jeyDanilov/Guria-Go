package com.remote

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.mapper.toFirestoreMap
import com.model.Order
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreOrderStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    suspend fun save(order: Order) {
        val uid = auth.currentUser?.uid
            ?: withTimeoutOrNull(3_000) { auth.signInAnonymously().await().user?.uid }
            ?: return
        db.collection("orders")
            .add(order.toFirestoreMap(uid, versionName()))
            .addOnFailureListener { Log.e("Orders", "Could not save order", it) }
    }

    private fun versionName(): String = runCatching {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName
    }.getOrNull().orEmpty()
}