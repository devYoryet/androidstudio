@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mysumativa.ui.theme

import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mysumativa.R
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController, users: MutableList<Triple<String, String, String>>) {
    // Caja principal que ocupa todo el tamaño de la pantalla
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de pantalla con la imagen de papas fritas
        val friesBackground = painterResource(id = R.drawable.papas_fritas_border_icon_claro)
        Image(
            painter = friesBackground,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escalar la imagen para que cubra toda la pantalla
        )

        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current

        // Función para hacer vibrar el dispositivo cuando hay un error
        fun vibrate() {
            // Obtener el Vibrator o VibratorManager según la versión de Android
            val vibrator = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(VibratorManager::class.java)
                vibratorManager.defaultVibrator
            } else {
                context.getSystemService(Vibrator::class.java)
            }

            // Si el Vibrator está disponible, iniciar la vibración
            vibrator?.let {
                Log.d("VibrationTest", "Vibration triggered")
                it.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                it.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }

        // Caja secundaria para manejar la detección de toques y ocultar el teclado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                    })
                }
        ) {
            // Columna para organizar los elementos de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center, // Centrar los elementos verticalmente
                horizontalAlignment = Alignment.CenterHorizontally // Centrar los elementos horizontalmente
            ) {
                // Variables para almacenar el correo y la contraseña ingresados
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                // Logo de la aplicación
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo de la aplicación",
                    modifier = Modifier.size(120.dp) // Tamaño del logo
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el logo y el siguiente elemento

                // Mostrar mensaje de error si existe
                errorMessage?.let {
                    Text(it, color = MaterialTheme.colorScheme.error) // Texto del mensaje de error en color rojo
                    Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el mensaje de error y el siguiente elemento
                }

                // Campo de texto para ingresar el correo electrónico
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it }, // Actualizar el valor cuando el usuario escribe
                    label = { Text("Correo electrónico") }, // Etiqueta del campo de texto
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho disponible
                        .padding(horizontal = 16.dp), // Padding horizontal de 16dp
                    shape = MaterialTheme.shapes.small, // Esquinas redondeadas pequeñas
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Color del borde cuando no está enfocado
                        containerColor = Color.White // Campo de texto blanco
                    )
                )

                Spacer(modifier = Modifier.height(8.dp)) // Espacio entre los campos de texto

                // Campo de texto para ingresar la contraseña
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it }, // Actualizar el valor cuando el usuario escribe
                    label = { Text("Contraseña") }, // Etiqueta del campo de texto
                    visualTransformation = PasswordVisualTransformation(), // Ocultar el texto de la contraseña con asteriscos
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho disponible
                        .padding(horizontal = 16.dp), // Padding horizontal de 16dp
                    shape = MaterialTheme.shapes.small, // Esquinas redondeadas pequeñas
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface, // Color del borde cuando no está enfocado
                        containerColor = Color.White // Campo de texto blanco
                    )
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los campos de texto y el botón

                // Botón para iniciar sesión
                Button(
                    onClick = {
                        val userExists = users.any { it.second == email.value && it.third == password.value }
                        if (userExists) {
                            keyboardController?.hide() // Ocultar el teclado si la autenticación es exitosa
                            navController.navigate("welcome") // Navegar a la pantalla de bienvenida
                        } else {
                            errorMessage = "Correo o contraseña incorrectos" // Mostrar mensaje de error si la autenticación falla
                            vibrate() // Hacer vibrar el dispositivo si la autenticación falla
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho disponible
                        .padding(horizontal = 16.dp), // Padding horizontal de 16dp
                    shape = MaterialTheme.shapes.medium, // Esquinas redondeadas medianas
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // Botón negro
                        contentColor = MaterialTheme.colorScheme.onPrimary // Texto en color claro
                    )
                ) {
                    Text("Iniciar Sesión")
                }

                Spacer(modifier = Modifier.height(24.dp)) // Espacio antes de los botones de registro y recuperación

                // Fila con botones para registrar o recuperar contraseña
                Row(
                    horizontalArrangement = Arrangement.Center, // Centrados horizontalmente
                    verticalAlignment = Alignment.CenterVertically, // Centrados verticalmente
                    modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho disponible
                ) {
                    TextButton(onClick = { navController.navigate("registro") }) {
                        Text(
                            "Registrar",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp, // Tamaño de fuente de 18sp
                                color = Color.Black, // Texto negro
                                fontWeight = FontWeight.Bold // Texto en negrita
                            )
                        )
                    }

                    // Separador visual entre los botones
                    Text(
                        " | ",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp, // Tamaño de fuente de 18sp
                            color = Color.Black, // Texto negro
                            fontWeight = FontWeight.Bold // Texto en negrita
                        )
                    )

                    TextButton(onClick = { navController.navigate("recuperar") }) {
                        Text(
                            "¿Olvidé Password?",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp, // Tamaño de fuente de 18sp
                                color = Color.Black, // Texto negro
                                fontWeight = FontWeight.Bold // Texto en negrita
                            )
                        )
                    }
                }
            }
        }
    }
}
