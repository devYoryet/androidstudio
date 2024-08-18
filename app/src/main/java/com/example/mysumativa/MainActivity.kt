package com.example.mysumativa

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.mysumativa.navigation.NavGraph
import com.example.mysumativa.ui.theme.MysumativaTheme

class MainActivity : ComponentActivity() {

    // Arreglo fijo de usuarios predefinidos
    private val predefinedUsers = listOf(
        Triple("juan.perez", "juan.perez@example.com", "password123"),
        Triple("maria.lopez", "maria.lopez@example.com", "password456"),
        Triple("carlos.sanchez", "carlos.sanchez@example.com", "password789"),
        Triple("ana.garcia", "ana.garcia@example.com", "passwordabc"),
        Triple("rodrigo.torres", "rodrigo.torres@example.com", "passworddef")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cargar usuarios desde SharedPreferences y combinarlos con los predefinidos
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val users = loadUsers(sharedPreferences)

        setContent {
            MysumativaTheme {
                // Inicializar el NavController para manejar la navegación
                val navController = rememberNavController()
                // Pasar la lista de usuarios al gráfico de navegación
                NavGraph(navController = navController, users = users)
            }
        }
    }

    // Función para cargar los usuarios almacenados en SharedPreferences y combinarlos con los predefinidos
    private fun loadUsers(sharedPreferences: android.content.SharedPreferences): MutableList<Triple<String, String, String>> {
        // Copiar usuarios predefinidos a una lista mutable para su uso en la aplicación
        val users = predefinedUsers.toMutableList()
        // Obtener el conjunto de usuarios almacenados en SharedPreferences
        val userSet = sharedPreferences.getStringSet("users", null)
        userSet?.forEach {
            // Dividir cada entrada en nombre de usuario, correo y contraseña y agregarlos a la lista
            val userParts = it.split(":")
            if (userParts.size == 3) {
                users.add(Triple(userParts[0], userParts[1], userParts[2]))
            }
        }
        return users
    }

    // Función para guardar un nuevo usuario en SharedPreferences
    fun saveUser(sharedPreferences: android.content.SharedPreferences, username: String, email: String, password: String) {
        val editor = sharedPreferences.edit()
        // Obtener los usuarios actuales de SharedPreferences
        val currentUsers = sharedPreferences.getStringSet("users", mutableSetOf()) ?: mutableSetOf()
        // Agregar el nuevo usuario en el formato "username:email:password"
        currentUsers.add("$username:$email:$password")
        // Guardar el conjunto de usuarios actualizado en SharedPreferences
        editor.putStringSet("users", currentUsers)
        editor.apply()
    }
}
