package com.example.mysumativa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainMenu(
    categories: List<String>, // Lista dinámica de categorías
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val selectedIndex = categories.indexOf(selectedCategory)

    if (categories.isNotEmpty()) {
        TabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = Color.White, // Fondo blanco del TabRow
            contentColor = Color.Black,    // Color del texto en pestañas no seleccionadas
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                // Indicador amarillo debajo de la pestaña seleccionada
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedIndex]),
                    color = Color.Yellow,
                    height = 4.dp
                )
            }
        ) {
            categories.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = {
                        onCategorySelected(title) // Actualiza la categoría seleccionada
                    },
                    text = {
                        Text(
                            title,
                            color = if (index == selectedIndex) Color.Black else Color.Gray
                        )
                    },
                    modifier = Modifier.background(if (index == selectedIndex) Color.Yellow else Color.White)
                )
            }
        }
    } else {
        // Mostrar algún mensaje si no hay categorías disponibles
        Text("No hay categorías disponibles", color = Color.Red)
    }
}
