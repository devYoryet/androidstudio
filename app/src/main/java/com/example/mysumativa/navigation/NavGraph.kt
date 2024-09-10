package com.example.mysumativa.navigation

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysumativa.ui.theme.LoginScreen
import com.example.mysumativa.ui.theme.RegistroScreen
import com.example.mysumativa.ui.theme.RecuperarPasswordScreen
import com.example.mysumativa.ui.theme.WelcomeScreen
import com.example.mysumativa.ui.screens.CartaScreen
import com.example.mysumativa.ui.screens.DeliveryScreen

import com.example.mysumativa.ui.screens.PedidoScreen



@Composable
fun NavGraph(
    navController: NavHostController,
    users: MutableList<Triple<String, String, String>>,
    tts: TextToSpeech // Añadimos el parámetro tts aquí
) {
    NavHost(navController = navController, startDestination = "login") {
        // Definir la ruta para la pantalla de login
        composable("login") { LoginScreen(navController, users) }
        // Definir la ruta para la pantalla de registro
        composable("registro") { RegistroScreen(navController, users, navController.context) }
        // Definir la ruta para la pantalla de recuperación de contraseña
        composable("recuperar") { RecuperarPasswordScreen(navController) }
        // Definir la ruta para la pantalla de bienvenida, pasando el parámetro tts
        composable("welcome") { WelcomeScreen(navController, tts) } // Corregido

        // Rutas adicionales para las nuevas pantallas
        composable("ver_carta") {
            CartaScreen(navController = navController) // Corregido: pasa el navController
        }

        composable("mi_pedido") {
            PedidoScreen()
        }

        composable("pedido_tipo") {
            DeliveryScreen()
        }
    }

    }

