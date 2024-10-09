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

    // Estado para manejar si los permisos han sido concedidos
    val locationPermissionGranted = remember { mutableStateOf(false) }

    // Solicitar permisos de ubicación cuando la pantalla se carga
    LaunchedEffect(Unit) {
        if (!checkLocationPermissions(context)) {
            requestLocationPermissions(context)
        } else {
            locationPermissionGranted.value = true
        }
    }

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
                        if (locationPermissionGranted.value) {
                            scope.launch {
                                requestLocation(context, fusedLocationClient) { address ->
                                    currentLocation = address
                                }
                            }
                        } else {
                            Toast.makeText(context, "Permisos de ubicación no concedidos", Toast.LENGTH_SHORT).show()
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

// Función para verificar si los permisos han sido concedidos
private fun checkLocationPermissions(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

// Función para solicitar permisos
private fun requestLocationPermissions(context: Context) {
    val activity = context as? android.app.Activity
    activity?.let {
        ActivityCompat.requestPermissions(
            it,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            1001  // Código de solicitud de permisos
        )
    }
}

// Función para obtener la ubicación actual
private fun requestLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFound: (String?) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
    ) {
        Toast.makeText(context, "Permisos de ubicación no concedidos", Toast.LENGTH_SHORT).show()
        return
    }

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address: Address? = addresses?.firstOrNull()
            onLocationFound(address?.getAddressLine(0))
        } ?: run {
            Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
        }
    }
}
