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
fun SelectSalsaScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Selecciona el tamaño de tu salsa", style = MaterialTheme.typography.h5)

        ProductCard(productName = "Salsa (G)", productPrice = "$800") {
            navController.navigate("select_salsa_type")
        }
        ProductCard(productName = "Salsa (M)", productPrice = "$1.000") {
            navController.navigate("select_salsa_type")
        }
        ProductCard(productName = "Salsa (XG)", productPrice = "$1.200") {
            navController.navigate("select_salsa_type")
        }
    }
}
