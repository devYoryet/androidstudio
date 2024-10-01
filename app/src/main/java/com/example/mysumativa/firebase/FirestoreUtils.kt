package com.example.mysumativa.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.mysumativa.ui.screens.viewmodels.CartaItem
import java.util.Date

// Función para verificar si un usuario existe en Firestore
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

object FirestoreUtils {

    private val firestore = FirebaseFirestore.getInstance()

    // Aquí almacenamos la referencia a la colección cartaItems en una variable
    private val cartaItemsCollection = firestore.collection("cartaItems")

    // Obtener todos los ítems de la carta
    suspend fun getCartaItems(): List<CartaItem> {
        return try {
            val snapshot = firestore.collection("cartaItems").get().await()
            val items = snapshot.documents.map { doc ->
                CartaItem(
                    id = doc.id,
                    descripcion = doc.getString("descripcion") ?: "",
                    precio = doc.getDouble("precio") ?: 0.0,
                    nombre = doc.getString("nombre") ?: "",
                    idCategoria = doc.getString("id_categoria") ?: ""
                )
            }
            Log.d("FirestoreUtils", "Productos obtenidos: $items")
            items
        } catch (e: Exception) {
            Log.e("FirestoreUtils", "Error al obtener carta items: ${e.message}")
            emptyList()
        }
    }

    // Obtener las categorías únicas desde Firestore
    suspend fun getCategorias(): List<String> {
        return try {
            // Reutilizamos la referencia a la colección
            val snapshot = cartaItemsCollection.get().await()
            snapshot.documents
                .mapNotNull { it.getString("id_categoria") }
                .distinct() // Obtener categorías únicas
        } catch (e: Exception) {
            Log.e("FirestoreUtils", "Error al obtener categorías: ${e.message}")
            emptyList()
        }
    }
    // Guardar el pedido en la colección "pedidos"
    fun guardarPedidoEnFirestore(userId: String, pedido: List<CartaItem>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val pedidoMap = hashMapOf(
            "userId" to userId,
            "fecha" to Date(),
            "productos" to pedido.map { producto ->
                hashMapOf(
                    "descripcion" to producto.descripcion,
                    "precio" to producto.precio,
                    "cantidad" to producto.cantidad  // Asume que tienes un campo cantidad en CartaItem
                )
            }
        )

        db.collection("pedidos")
            .add(pedidoMap)
            .addOnSuccessListener {
                Log.d("FirestoreUtils", "Pedido guardado con éxito")
                onSuccess()
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreUtils", "Error al guardar el pedido: ${exception.message}")
                onFailure(exception)
            }
    }

}
