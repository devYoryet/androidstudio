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

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import com.example.mysumativa.R
import kotlinx.coroutines.tasks.await
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, context: Context) {
    // Variables para almacenar el nombre de usuario, correo electrónico y contraseña
    val db = FirebaseFirestore.getInstance()
    val usersRef = db.collection("users")

    // Fondo de pantalla
    Box(modifier = Modifier.fillMaxSize()) {
        val friesBackground = painterResource(id = R.drawable.papas_fritas_border_icon_claro) // Imagen de fondo
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
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icono y título de la pantalla de registro
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Icono de registro",
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "Registrar Usuarios",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }

            // Campo de texto para ingresar el nombre de usuario
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para ingresar el correo electrónico
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de texto para ingresar la contraseña
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para registrar al usuario
            Button(
                onClick = {
                    if (username.value.isNotBlank() && email.value.isNotBlank() && password.value.isNotBlank()) {
                        coroutineScope.launch {
                            // Consulta para verificar si el usuario ya existe
                            val querySnapshot = usersRef
                                .whereEqualTo("email", email.value)
                                .get()
                                .await()

                            if (querySnapshot.isEmpty) {
                                // Si no existe, creamos un nuevo documento con ID autogenerado
                                val newUser = hashMapOf(
                                    "username" to username.value,
                                    "email" to email.value,
                                    "password" to password.value
                                )

                                usersRef.add(newUser)
                                    .addOnSuccessListener {
                                        // Navegar a la pantalla de login
                                        navController.navigate("login")
                                    }
                                    .addOnFailureListener {
                                        errorMessage = "Error al registrar el usuario"
                                    }
                            } else {
                                errorMessage = "El usuario o correo ya existe"
                            }
                        }
                    } else {
                        errorMessage = "Por favor, complete todos los campos"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Registrar Usuario", fontWeight = FontWeight.Bold)
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón para volver a la pantalla de login
            TextButton(onClick = { navController.navigate("login") }) {
                Text(
                    "Volver al Login",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
