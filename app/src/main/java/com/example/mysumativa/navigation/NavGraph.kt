package com.example.mysumativa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mysumativa.ui.theme.LoginScreen
import com.example.mysumativa.ui.theme.RegistroScreen
import com.example.mysumativa.ui.theme.RecuperarPasswordScreen
import com.example.mysumativa.ui.theme.WelcomeScreen

@Composable
fun NavGraph(navController: NavHostController, users: MutableList<Triple<String, String, String>>) {
    NavHost(navController = navController, startDestination = "login") {
        // Definir la ruta para la pantalla de login
        composable("login") { LoginScreen(navController, users) }
        // Definir la ruta para la pantalla de registro
        composable("registro") { RegistroScreen(navController, users, navController.context) }
        // Definir la ruta para la pantalla de recuperación de contraseña
        composable("recuperar") { RecuperarPasswordScreen(navController) }
        // Definir la ruta para la pantalla de bienvenida
        composable("welcome") { WelcomeScreen(navController) }
    }
}
