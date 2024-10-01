package com.example.mysumativa.navigation

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysumativa.MainActivity
import com.example.mysumativa.ui.theme.LoginScreen
import com.example.mysumativa.ui.theme.RegistroScreen
import com.example.mysumativa.ui.theme.RecuperarPasswordScreen
import com.example.mysumativa.ui.theme.WelcomeScreen
import com.example.mysumativa.ui.screens.CartaScreen
import com.example.mysumativa.ui.screens.DeliveryScreen
import com.example.mysumativa.ui.screens.PedidoScreen
import com.example.mysumativa.ui.screens.viewmodels.CartaViewModel
import com.example.mysumativa.ui.screens.WelcomeDeliveryScreen
import com.example.mysumativa.ui.screens.WelcomePedidoScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    mainActivity: MainActivity,  // Se pasa el mainActivity como parámetro
    tts: TextToSpeech // Se pasa el tts para las pantallas que lo requieren
) {
    val context = LocalContext.current // Obtén el contexto actual si es necesario

    NavHost(navController = navController, startDestination = "login") {
        // Definir la ruta para la pantalla de login
        composable("login") {
            LoginScreen(navController = navController, mainActivity = mainActivity)
        }
        // Definir la ruta para la pantalla de registro
        composable("registro") {
            RegistroScreen(navController = navController, context = context)
        }
        // Definir la ruta para la pantalla de recuperación de contraseña
        composable("recuperar") {
            RecuperarPasswordScreen(navController = navController)
        }
        // Definir la ruta para la pantalla de bienvenida, pasando el parámetro tts
        composable("welcome") {
            WelcomeScreen(navController = navController, tts = tts)
        }

        // Rutas adicionales para las nuevas pantallas
        composable("ver_carta") {

            val viewModel: CartaViewModel = viewModel()  // Compartimos el mismo ViewModel
            CartaScreen(navController = navController, tts = tts, viewModel = viewModel)
        }

        composable("mi_pedido") {
            val viewModel: CartaViewModel = viewModel()  // Compartimos el mismo ViewModel
            PedidoScreen(navController = navController, tts = tts, viewModel = viewModel)
        }

        // Pasamos el navController a DeliveryScreen para navegar correctamente
        composable("delivery_screen") {
                DeliveryScreen(navController = navController)
        }
        composable("welcome_delivery") {
            WelcomeDeliveryScreen(navController = navController)
        }
        // Nueva ruta para WelcomePedidoScreen
        composable("welcome_pedido") {
            // Aquí puedes pasar la lista de productos y el total del pedido
            WelcomePedidoScreen(
                navController = navController,
                pedido = listOf("Papas Fritas G", "Salsas Camaron - Queso", "Bebida Lata Coca - Cola"),  // Ejemplo de productos
                total = 12500.0  // Ejemplo de total
            )
        }
    }
}
