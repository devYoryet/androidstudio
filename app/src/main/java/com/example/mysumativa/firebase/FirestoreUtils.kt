package com.example.mysumativa.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

suspend fun checkUserExists(email: String, password: String): Boolean {
    val firestore = FirebaseFirestore.getInstance()
    val usersCollection = firestore.collection("users")

    return try {
        Log.d("FirestoreCheck", "Consultando Firestore con email: $email y password: $password")

        // Realizar la consulta a la colección de usuarios donde el email y la contraseña coincidan
        val querySnapshot = usersCollection
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .await()

        Log.d("FirestoreCheck", "Documentos encontrados: ${querySnapshot.size()}")

        // Si hay un documento en la respuesta, significa que el usuario existe
        !querySnapshot.isEmpty
    } catch (e: Exception) {
        Log.e("FirestoreCheck", "Error en la consulta a Firestore: ${e.message}")
        e.printStackTrace()
        false
    }
}
