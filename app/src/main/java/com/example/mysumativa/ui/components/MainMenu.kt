package com.example.mysumativa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainMenu(navController: NavController, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val tabs = listOf("Papas Fritas", "Agregados", "Bebestibles", "Extras")
    val selectedIndex = tabs.indexOf(selectedCategory)

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
        tabs.forEachIndexed { index, title ->
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
}
