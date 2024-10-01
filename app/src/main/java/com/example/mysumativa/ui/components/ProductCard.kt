package com.example.mysumativa.ui.components

import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mysumativa.R

@Composable
fun ProductCard(productName: String, productPrice: String, onAddClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = productName, style = MaterialTheme.typography.h6, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = productPrice, style = MaterialTheme.typography.body1, color = Color.Gray)
            }

            // Botón de agregar
            IconButton(onClick = onAddClick) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Agregar",
                    tint = Color.Yellow,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
