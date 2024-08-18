@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mysumativa.ui.theme

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mysumativa.R

@Composable
fun RecuperarPasswordScreen(navController: NavController) {


    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo con rayas en la parte superior
        val friesBackground = painterResource(id = R.drawable.papas_fritas_border_icon_claro) // Usa la imagen completa de fondo
        Image(
            painter = friesBackground,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    // Variable para almacenar el correo electrónico ingresado
    val email = remember { mutableStateOf("") }

    // Estructura de la pantalla utilizando un Column para apilar elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize() // Llenar todo el tamaño disponible de la pantalla
            .padding(16.dp), // Añadir padding de 16dp en todos los lados
        verticalArrangement = Arrangement.Center, // Centrar los elementos verticalmente
        horizontalAlignment = Alignment.CenterHorizontally // Alinear los elementos horizontalmente al centro
    ) {
        // Título de la pantalla
        Text(
            text = "Recuperar Contraseña",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 24.sp, // Tamaño de fuente de 24sp
                fontWeight = FontWeight.Bold, // Texto en negrita
                color = Color.Black // Color negro
            )
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio de 16dp entre el título y el siguiente elemento

        // Campo de texto para ingresar el correo electrónico
        OutlinedTextField(
            value = email.value, // Valor actual del campo de texto
            onValueChange = { email.value = it }, // Actualizar el valor del campo cuando el usuario escribe
            label = { Text("Email") }, // Etiqueta del campo de texto
            modifier = Modifier.fillMaxWidth(), // El campo de texto ocupa todo el ancho disponible
            shape = MaterialTheme.shapes.small, // Esquina redondeada del campo de texto
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Color del borde cuando no está enfocado
                containerColor = Color.White // Color de fondo del campo de texto
            )
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio de 16dp entre el campo de texto y el botón

        // Botón para enviar el correo de recuperación
        Button(
            onClick = {
                // Aquí iría la lógica para enviar el correo de recuperación
            },
            modifier = Modifier.fillMaxWidth(), // El botón ocupa todo el ancho disponible
            shape = MaterialTheme.shapes.medium, // Esquina redondeada del botón
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black, // Color de fondo del botón
                contentColor = Color.White // Color del texto del botón
            )
        ) {
            Text("Enviar Correo", fontWeight = FontWeight.Bold) // Texto dentro del botón
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espacio de 8dp entre el botón y el enlace de volver

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
