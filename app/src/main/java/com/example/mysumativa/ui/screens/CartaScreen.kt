package com.example.mysumativa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mysumativa.ui.components.MainMenu
import com.example.mysumativa.ui.components.ProductCard
import com.example.mysumativa.R // Asegúrate de importar el recurso

@Composable
fun CartaScreen(navController: NavController) {
    // Estado para rastrear la categoría seleccionada
    var selectedCategory by remember { mutableStateOf("Papas Fritas") }

    // Imagen de fondo (papas fritas)
    val friesBackground = painterResource(id = R.drawable.papas_fritas_border_icon_claro)

    // Contenedor que envuelve la imagen de fondo y el contenido principal
    Box(modifier = Modifier.fillMaxSize()) {

        // Imagen de fondo (cubriendo toda la pantalla)
        Image(
            painter = friesBackground,
            contentDescription = null, // No es necesario un description en este caso
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escalar para cubrir la pantalla
        )

        // Contenido principal de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Menú superior para seleccionar categoría
            MainMenu(
                navController = navController,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    // Actualiza la categoría seleccionada al hacer clic en una pestaña
                    selectedCategory = category
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título de la categoría seleccionada (Papas Fritas, Agregados, etc.)
            Text(text = selectedCategory, style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Volver" para regresar a la pantalla anterior (WelcomeScreen)
            Button(
                onClick = {
                    navController.popBackStack() // Acción para regresar a la pantalla anterior
                },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Volver")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar los productos según la categoría seleccionada
            when (selectedCategory) {
                "Papas Fritas" -> {
                    // Productos de la categoría "Papas Fritas"
                    ProductCard(productName = "Papas (G)", productPrice = "$4.000") {
                        // Acción al agregar papas (G)
                    }
                    ProductCard(productName = "Papas (M)", productPrice = "$3.500") {
                        // Acción al agregar papas (M)
                    }
                    ProductCard(productName = "Papas (XG)", productPrice = "$4.500") {
                        // Acción al agregar papas (XG)
                    }
                }
                "Agregados" -> {
                    // Productos de la categoría "Agregados"
                    ProductCard(productName = "Aji Cristal al Oliva (G)", productPrice = "$800") {
                        // Acción al agregar agregado (G)
                    }
                    ProductCard(productName = "Lactonesa de Cilantro (G)", productPrice = "$800") {
                        // Acción al agregar agregado (G)
                    }
                    ProductCard(productName = "Mayonesa al Ajo (XG)", productPrice = "$1.000") {
                        // Acción al agregar agregado (XG)
                    }
                }
                "Bebestibles" -> {
                    // Productos de la categoría "Bebestibles"
                    ProductCard(productName = "Agua Mineral", productPrice = "$2.200") {
                        // Acción al agregar bebida
                    }
                    ProductCard(productName = "Té Verde Mango y Maracuyá", productPrice = "$2.200") {
                        // Acción al agregar bebida
                    }
                }
                "Extras" -> {
                    // Productos de la categoría "Extras"
                    ProductCard(productName = "Salsa Barbacoa Casera", productPrice = "$1.200") {
                        // Acción al agregar extra
                    }
                    ProductCard(productName = "Salsa de Queso Cheddar", productPrice = "$1.600") {
                        // Acción al agregar extra
                    }
                }
            }
        }
    }
}
