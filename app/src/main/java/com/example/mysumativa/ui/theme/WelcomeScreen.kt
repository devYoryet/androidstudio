package com.example.mysumativa.ui.theme

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp

import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import com.example.mysumativa.R

@Composable
fun WelcomeScreen(navController: NavController, tts: TextToSpeech) {
    var searchText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Fondo de pantalla con papas fritas
    val friesBackground = painterResource(id = R.drawable.papas_fritas_border_icon_claro)

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = friesBackground,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escalar la imagen para que cubra toda la pantalla
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Barra de búsqueda
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar en la carta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.medium),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray)
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Fila de iconos grandes
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                MainMenuButton(
                    icon = painterResource(id = R.drawable.menu_icon),
                    label = "Ver Carta",
                    onClick = {
                        navController.navigate("ver_carta")
                    },
                    iconSize = 100.dp // Aumento de tamaño
                )

                MainMenuButton(
                    icon = painterResource(id = R.drawable.cart_icon),
                    label = "Mi Pedido",
                    onClick = {
                        navController.navigate("mi_pedido")
                    },
                    iconSize = 100.dp // Aumento de tamaño
                )

                MainMenuButton(
                    icon = painterResource(id = R.drawable.delivery_icon),
                    label = "Delivery",
                    onClick = {
                        navController.navigate("pedido_tipo")
                    },
                    iconSize = 100.dp // Aumento de tamaño
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de accesibilidad en negro con blanco
            Button(
                onClick = {
                    coroutineScope.launch {
                        speakText(tts, "Hola, este es mi pedido, por favor léalo.")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.Black, shape = MaterialTheme.shapes.medium), // Fondo negro
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Accesibilidad TTS", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mostrar Pedido con Voz", fontSize = 18.sp, color = Color.White) // Texto blanco
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Imagen de promoción
            Image(
                painter = painterResource(id = R.drawable.promo_image),
                contentDescription = "Imagen de promoción",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFFE3F2FD))
            )
        }
    }
}

@Composable
fun MainMenuButton(icon: Painter, label: String, onClick: () -> Unit, iconSize: Dp) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icono más grande para mejorar visibilidad
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(iconSize) // Tamaño personalizable
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 16.sp, color = Color(0xFF424242))
    }
}

fun speakText(tts: TextToSpeech, text: String) {
    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
}