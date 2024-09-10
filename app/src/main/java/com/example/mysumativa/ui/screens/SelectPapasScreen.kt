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
fun SelectPapasScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Selecciona el tamaño de tus papas", style = MaterialTheme.typography.h5)

        // Tarjetas para seleccionar el tamaño de las papas
        ProductCard(productName = "Papas (G)", productPrice = "$3.500") {
            navController.navigate("select_salsa")
        }
        ProductCard(productName = "Papas (M)", productPrice = "$4.000") {
            navController.navigate("select_salsa")
        }
        ProductCard(productName = "Papas (XG)", productPrice = "$4.500") {
            navController.navigate("select_salsa")
        }
    }
}
