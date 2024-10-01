
package com.example.mysumativa.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mysumativa.ui.theme.MysumativaTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun DeliveryScreen(navController: NavController) {
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var comuna by remember { mutableStateOf(TextFieldValue("")) }
    var nota by remember { mutableStateOf(TextFieldValue("")) }
    var ciudadSeleccionada by remember { mutableStateOf("") }
    var locationPermissionGranted by remember { mutableStateOf(false) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) } // Variable para la ubicación del usuario

    val ciudadesDeChile = listOf("Santiago", "Valparaíso", "Concepción", "La Serena", "Antofagasta")

    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val coroutineScope = rememberCoroutineScope()

    // Permisos de ubicación
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        locationPermissionGranted = isGranted
        if (isGranted) {
            // Obtener la ubicación actual
            coroutineScope.launch {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        userLocation = location?.let {
                            LatLng(it.latitude, it.longitude) // Convertir a LatLng
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Información de Entrega") }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(16.dp)
            ) {
                Text(text = "Ingrese sus datos de entrega", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))

                // Mapa para mostrar ubicación
                userLocation?.let { location -> // Asegurarse de que userLocation es válido
                    // Configurar el estado de la cámara para el mapa
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(location, 12f) // Ubicación del usuario
                    }

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(position = location), // Usar MarkerState con position
                            title = "Ubicación Actual"
                        )
                    }
                } ?: run {
                    Text(text = "Obteniendo ubicación...", style = MaterialTheme.typography.body1)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dropdown para seleccionar una ciudad
                Text(text = "Seleccione su ciudad")
                var expanded by remember { mutableStateOf(false) }
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = { expanded = !expanded }) {
                        Text(text = ciudadSeleccionada.ifEmpty { "Seleccione una ciudad" })
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        ciudadesDeChile.forEach { ciudad ->
                            DropdownMenuItem(onClick = {
                                ciudadSeleccionada = ciudad
                                expanded = false
                            }) {
                                Text(text = ciudad)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dirección input field
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Comuna input field
                OutlinedTextField(
                    value = comuna,
                    onValueChange = { comuna = it },
                    label = { Text("Comuna") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Nota opcional input field
                OutlinedTextField(
                    value = nota,
                    onValueChange = { nota = it },
                    label = { Text("Nota Opcional") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para grabar y redirigir a WelcomeScreen
                Button(
                    onClick = {
                        // Redirige a WelcomeScreen
                        navController.navigate("welcome")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Grabar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para regresar al menú (WelcomeScreen)
                Button(
                    onClick = {
                        navController.navigate("welcome")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Ir al Menú")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeliveryScreenPreview() {
    MysumativaTheme {
        DeliveryScreen(navController = rememberNavController())
    }
}
