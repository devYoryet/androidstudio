package com.example.mysumativa.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mysumativa.ui.components.MainMenu
import com.example.mysumativa.ui.components.ProductCard
import com.example.mysumativa.ui.screens.viewmodels.CartaViewModel

@Composable
fun CartaScreen(
    navController: NavController,
    tts: TextToSpeech,
    viewModel: CartaViewModel = viewModel()
) {
    var selectedCategory by remember { mutableStateOf("") }
    // Obtener estado de categorías y productos
    val categorias by viewModel.categorias.collectAsState()
    val cartaItems by viewModel.cartaItems.collectAsState()
    val pedido by viewModel.pedido.collectAsState()

    // Seleccionar primera categoría por defecto
    if (selectedCategory.isEmpty() && categorias.isNotEmpty()) {
        selectedCategory = categorias.first()
    }

    // Configurar el TTS para hablar más rápido y claro
    tts.setSpeechRate(1.1f)
    tts.setPitch(1.2f)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Menú superior para seleccionar categorías
            MainMenu(
                categories = categorias,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título de la categoría seleccionada
            Text(
                text = selectedCategory,
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Visualización de la lista de productos filtrados por categoría
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartaItems.filter { it.idCategoria == selectedCategory }) { item ->
                    ProductCard(productName = item.descripcion, productPrice = "${item.precio}") {
                        viewModel.agregarAlPedido(item)  // Usar el ViewModel para agregar al pedido
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // Contenedor visual del pedido
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF0F0F0))  // Fondo claro para separar visualmente
                    .padding(16.dp)
            ) {
                Column {
                    // Título de la sección de pedido
                    Text(
                        text = "Tu Pedido",
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Mostrar productos seleccionados en el pedido
                    if (pedido.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.height(150.dp)) {  // Limitar la altura del LazyColumn
                            items(pedido) { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    // Mostrar el nombre, cantidad y el precio del producto
                                    Text(
                                        text = "${item.descripcion} (x${item.cantidad}): $${item.precio * item.cantidad}",
                                        style = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
                                    )

                                    // Botón para eliminar el producto del pedido (disminuir la cantidad)
                                    IconButton(onClick = { viewModel.eliminarDelPedido(item) }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Eliminar",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Mostrar el total
                        val total = viewModel.calcularTotalPedido()
                        Text(
                            text = "Total: $${total}",
                            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                        )
                    } else {
                        Text(text = "No has agregado productos al pedido")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón de confirmar pedido
                    Button(
                        onClick = {
                            navController.navigate("mi_pedido")  // Navega a la pantalla de pedido
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
                    ) {
                        Text(
                            text = "Confirmar Pedido",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para leer el pedido en voz alta (Text-to-Speech)
                    Button(
                        onClick = {
                            // Construir el texto del pedido en español
                            val pedidoTexto = buildString {
                                append("Necesito el siguiente pedido: ")
                                pedido.forEach { item ->
                                    // Formatear el precio sin decimales si es un número entero
                                    val precioFormateado = if (item.precio % 1 == 0.0) {
                                        item.precio.toInt().toString() // Convertir a entero si no hay decimales
                                    } else {
                                        String.format("%.2f", item.precio) // Mostrar hasta 2 decimales si los hay
                                    }

                                    append("Producto: ${item.descripcion}, con un precio de $precioFormateado pesos. ")
                                }
                                append("Gracias.")
                            }
                            tts.speak(pedidoTexto, TextToSpeech.QUEUE_FLUSH, null, null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03A9F4))
                    ) {
                        Text(
                            text = "Leer Pedido en Voz Alta",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
