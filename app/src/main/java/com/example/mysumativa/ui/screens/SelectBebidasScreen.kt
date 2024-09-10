package com.example.mysumativa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mysumativa.ui.components.ProductCard

@Composable
fun SelectBebidasScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Selecciona tu bebida", style = MaterialTheme.typography.h5)

        ProductCard(productName = "Agua Mineral", productPrice = "$2.200") {
            // Acción al seleccionar esta bebida
        }
        ProductCard(productName = "Té Verde Mango y Maracuyá", productPrice = "$2.200") {
            // Acción al seleccionar esta bebida
        }
    }
}
