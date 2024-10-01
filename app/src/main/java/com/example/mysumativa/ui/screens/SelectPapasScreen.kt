package com.example.mysumativa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mysumativa.ui.components.ProductCard
import com.example.mysumativa.ui.screens.viewmodels.CartaViewModel

@Composable
fun SelectPapasScreen(navController: NavController, viewModel: CartaViewModel = viewModel()) {
    // Obtener el estado de cartaItems directamente
    val cartaItemsState = viewModel.cartaItems.collectAsState()

    // Acceder al valor de la lista directamente
    val cartaItems = cartaItemsState.value
    val papasItems = cartaItems.filter { it.idCategoria == "Papas Fritas" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Selecciona el tamaño de tus papas", style = MaterialTheme.typography.h5)

        // Mostramos los productos de la categoría Papas Fritas
        papasItems.forEach { item ->
            ProductCard(productName = item.nombre, productPrice = "${item.precio}") {
                navController.navigate("select_salsa")
            }
        }
    }
}
