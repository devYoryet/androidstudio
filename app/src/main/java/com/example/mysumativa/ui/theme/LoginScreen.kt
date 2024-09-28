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
import com.example.mysumativa.MainActivity

import kotlinx.coroutines.launch

import androidx.compose.runtime.rememberCoroutineScope
import com.example.mysumativa.firebase.checkUserExists


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController, mainActivity: MainActivity) {
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
        val coroutineScope = rememberCoroutineScope()

        // Función para hacer vibrar el dispositivo cuando hay un error
        fun vibrate() {
            val vibrator = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(VibratorManager::class.java)
                vibratorManager.defaultVibrator
            } else {
                context.getSystemService(Vibrator::class.java)
            }

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                // Logo de la aplicación
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo de la aplicación",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar mensaje de error si existe
                errorMessage?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Campo de texto para ingresar el correo electrónico
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        containerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para iniciar sesión
                Button(
                    onClick = {
                        // Llamamos a la función checkUserExists en una corrutina
                        coroutineScope.launch {
                            val userExists = checkUserExists(email.value, password.value)
                            if (userExists) {
                                keyboardController?.hide()
                                navController.navigate("welcome") // Navegar a la pantalla de bienvenida
                            } else {
                                errorMessage = "Correo o contraseña incorrectos"
                                vibrate() // Hacer vibrar el dispositivo si la autenticación falla
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Iniciar Sesión")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Fila con botones para registrar o recuperar contraseña
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { navController.navigate("registro") }) {
                        Text(
                            "Registrar",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Text(" | ", style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ))

                    TextButton(onClick = { navController.navigate("recuperar") }) {
                        Text(
                            "¿Olvidé Password?",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}