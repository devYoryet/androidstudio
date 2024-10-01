package com.example.mysumativa.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mysumativa.R

@Composable
fun WelcomePedidoScreen(navController: NavController, pedido: List<String>, total: Double) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedido en Curso") },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de cabecera
            Image(
                painter = painterResource(id = R.drawable.pedido_in_curso_image),
                contentDescription = "Pedido en curso",
                modifier = Modifier
                    .width(250.dp)  // Establece un ancho específico
                    .height(150.dp)  // Establece un alto específico
                    .padding(16.dp),  // Añade un padding para que no esté pegada a los bordes
                contentScale = ContentScale.Crop // Ajusta cómo se escalará la imagen
            )


            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Tu pedido está en curso!",
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF6200EE)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar lista de productos
            Text(
                text = "Resumen de tu pedido:",
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    pedido.forEach { producto ->
                        Text(
                            text = producto,
                            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar total del pedido
            Text(
                text = "Total: $${total}",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF424242)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de regreso
            Button(
                onClick = { navController.navigate("welcome") }, // Cambiar a la pantalla que necesites
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Volver al Menú", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
