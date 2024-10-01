package com.example.mysumativa.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.mysumativa.ui.screens.viewmodels.CartaViewModel
import com.example.mysumativa.R
import kotlinx.coroutines.delay

@Composable
fun PedidoScreen(
    navController: NavController,
    tts: TextToSpeech,
    viewModel: CartaViewModel = viewModel()
) {
    // Temporizador que espera 4 segundos antes de redirigir
    LaunchedEffect(Unit) {
        delay(4000)  // Espera 4 segundos
        navController.navigate("delivery_screen")  // Navegar a la pantalla de entrega (cambiar según tu ruta)
    }

    // Obtener los productos seleccionados del ViewModel
    val pedido by viewModel.pedido.collectAsState()

    // Composición para animación con Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Fondo blanco
        contentAlignment = Alignment.Center // Centrar el contenido
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mostrar la animación de confeti
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp) // Ajusta el tamaño de la animación
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mi Pedido Confirmado",
                fontSize = 32.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar los productos en el pedido si existen
            if (pedido.isNotEmpty()) {
                // Listar los productos
                pedido.forEach { item ->
                    Text(
                        text = "${item.descripcion}: $${item.precio}",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Calcular el total y mostrarlo
                val total = viewModel.calcularTotalPedido()
                Text(
                    text = "Total: $${total}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            } else {
                // Mensaje cuando no hay productos en el pedido
                Text(text = "Tu pedido ha sido creado con éxito", fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}
