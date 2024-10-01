package com.example.mysumativa.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager  // Importar PackageManager
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mysumativa.ui.theme.MysumativaTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun WelcomeDeliveryScreen(navController: NavController) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val scope = rememberCoroutineScope()

    // Simulación de direcciones favoritas
    val direccionesFavoritas = remember { mutableStateOf(listOf("Av. Siempre Viva 742", "Calle Falsa 123")) }

    // Para mostrar la dirección georreferencial
    var currentLocation by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Delivery - Direcciones Guardadas") }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Selecciona una dirección favorita o detecta tu ubicación",
                    style = MaterialTheme.typography.h6
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar lista de direcciones favoritas
                Text(text = "Direcciones Favoritas:")
                LazyColumn(modifier = Modifier.height(150.dp)) {
                    items(direccionesFavoritas.value) { direccion ->
                        Button(
                            onClick = { /* Lógica para seleccionar la dirección */ },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = direccion)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para obtener la ubicación actual
                Button(
                    onClick = {
                        scope.launch {
                            requestLocation(context, fusedLocationClient) { address ->
                                currentLocation = address
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Detectar mi Ubicación Actual")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar la dirección georreferenciada
                currentLocation?.let {
                    Text(text = "Ubicación actual: $it")

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón para guardar la dirección actual
                    Button(
                        onClick = {
                            // Guardar la dirección actual como favorita
                            direccionesFavoritas.value = direccionesFavoritas.value + it
                            Toast.makeText(context, "Dirección guardada como favorita", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Guardar como Dirección Favorita")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        navController.navigate("mi_pedido")  // Ir a la pantalla de pedidos
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Confirmar Dirección y Ver mi Pedido")
                }
            }
        }
    )
}

// Función para obtener la ubicación actual
private fun requestLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFound: (String?) -> Unit
) {
    // Verificar si se tienen permisos de ubicación
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ) {
        Toast.makeText(context, "Permisos de ubicación no concedidos", Toast.LENGTH_SHORT).show()
        return
    }

    // Obtener la última ubicación
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)

            // Verificar si las direcciones no son nulas y obtener la primera
            val address: Address? = addresses?.firstOrNull()

            // Si encontramos una dirección, la pasamos a la función de callback
            onLocationFound(address?.getAddressLine(0))
        } ?: run {
            Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeDeliveryScreenPreview() {
    MysumativaTheme {
        WelcomeDeliveryScreen(navController = rememberNavController())
    }
}
