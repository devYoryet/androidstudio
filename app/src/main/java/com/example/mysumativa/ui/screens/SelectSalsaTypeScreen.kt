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
fun SelectSalsaTypeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Selecciona tu salsa", style = MaterialTheme.typography.h5)

        ProductCard(productName = "Salsa Mayonesa Casera", productPrice = "$1.000") {
            // Acción al seleccionar esta salsa
        }
        ProductCard(productName = "Salsa Barbacoa", productPrice = "$1.200") {
            // Acción al seleccionar esta salsa
        }
    }
}
