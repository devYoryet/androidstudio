@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mysumativa.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mysumativa.MainActivity
import com.example.mysumativa.R

@Composable
fun RegistroScreen(navController: NavController, users: MutableList<Triple<String, String, String>>, context: Context) {
    // Variables para almacenar el nombre de usuario, correo electrónico y contraseña

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo con rayas en la parte superior
        val friesBackground = painterResource(id = R.drawable.papas_fritas_border_icon_claro) // Usa la imagen completa de fondo
        Image(
            painter = friesBackground,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    val username = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Obtener SharedPreferences para guardar los datos del usuario registrado
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    // Configuración de la estructura visual de la pantalla utilizando un Column
    Column(
        modifier = Modifier
            .fillMaxSize() // Ocupa todo el tamaño disponible
            .padding(16.dp), // Padding de 16dp en todos los lados
        verticalArrangement = Arrangement.Center, // Centrar verticalmente los elementos
        horizontalAlignment = Alignment.CenterHorizontally // Alinear los elementos al centro horizontalmente
    ) {
        // Fila que contiene el ícono y el título de la pantalla
        Row(
            verticalAlignment = Alignment.CenterVertically, // Alinear el ícono y el texto verticalmente al centro
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaciado horizontal entre el ícono y el texto
            modifier = Modifier.padding(bottom = 16.dp) // Padding inferior de 16dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Cargar recurso de imagen
                contentDescription = "Icono de registro", // Descripción del ícono para accesibilidad
                modifier = Modifier.size(40.dp) // Tamaño del ícono de 40dp
            )
            Text(
                text = "Registrar Usuarios",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 24.sp, // Tamaño de fuente de 24sp
                    fontWeight = FontWeight.Bold, // Texto en negrita
                    color = Color.Black // Color negro
                )
            )
        }

        // Campo de texto para ingresar el nombre de usuario
        OutlinedTextField(
            value = username.value, // Valor del campo de texto
            onValueChange = { username.value = it }, // Actualizar el valor cuando el usuario escribe
            label = { Text("Nombre de usuario") }, // Etiqueta del campo de texto
            modifier = Modifier.fillMaxWidth(), // El campo ocupa todo el ancho disponible
            shape = MaterialTheme.shapes.small, // Esquinas redondeadas pequeñas
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Color del borde cuando no está enfocado
                containerColor = Color.White // Color de fondo del campo de texto
            )
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio de 8dp entre campos

        // Campo de texto para ingresar el correo electrónico
        OutlinedTextField(
            value = email.value, // Valor del campo de texto
            onValueChange = { email.value = it }, // Actualizar el valor cuando el usuario escribe
            label = { Text("Correo electrónico") }, // Etiqueta del campo de texto
            modifier = Modifier.fillMaxWidth(), // El campo ocupa todo el ancho disponible
            shape = MaterialTheme.shapes.small, // Esquinas redondeadas pequeñas
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Color del borde cuando no está enfocado
                containerColor = Color.White // Color de fondo del campo de texto
            )
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio de 8dp entre campos

        // Campo de texto para ingresar la contraseña
        OutlinedTextField(
            value = password.value, // Valor del campo de texto
            onValueChange = { password.value = it }, // Actualizar el valor cuando el usuario escribe
            label = { Text("Contraseña") }, // Etiqueta del campo de texto
            visualTransformation = PasswordVisualTransformation(), // Ocultar el texto con asteriscos
            modifier = Modifier.fillMaxWidth(), // El campo ocupa todo el ancho disponible
            shape = MaterialTheme.shapes.small, // Esquinas redondeadas pequeñas
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Color del borde cuando no está enfocado
                containerColor = Color.White // Color de fondo del campo de texto
            )
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio de 16dp antes del botón

        // Botón para registrar al usuario
        Button(
            onClick = {
                if (username.value.isNotBlank() && email.value.isNotBlank() && password.value.isNotBlank()) {
                    val userExists = users.any { it.first == username.value || it.second == email.value } // Verifica si el usuario o correo ya existe
                    if (!userExists) {
                        users.add(Triple(username.value, email.value, password.value)) // Agrega el usuario a la lista
                        (context as MainActivity).saveUser(sharedPreferences, username.value, email.value, password.value) // Guarda el usuario en SharedPreferences
                        navController.navigate("login") // Navega a la pantalla de login
                    } else {
                        errorMessage = "El usuario o correo ya existe" // Muestra mensaje de error si el usuario ya existe
                    }
                } else {
                    errorMessage = "Por favor, complete todos los campos" // Muestra mensaje de error si faltan campos por llenar
                }
            },
            modifier = Modifier.fillMaxWidth(), // El botón ocupa todo el ancho disponible
            shape = MaterialTheme.shapes.medium, // Esquinas redondeadas medianas
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // Color de fondo del botón
                contentColor = Color.White // Color del texto del botón
            )
        ) {
            Text("Registrar Usuario", fontWeight = FontWeight.Bold) // Texto dentro del botón
        }

        // Mostrar mensaje de error si existe
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp)) // Espacio de 8dp antes del mensaje de error
            Text(it, color = MaterialTheme.colorScheme.error) // Texto del mensaje de error en color rojo
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio de 8dp antes del botón de volver

        // Vínculo para volver a la pantalla de Login
        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                "Volver al Login",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp, // Tamaño de fuente de 16sp
                    color = Color.Black, // Color negro
                    fontWeight = FontWeight.Bold // Texto en negrita
                     )
                )
            }
        }
    }
}
