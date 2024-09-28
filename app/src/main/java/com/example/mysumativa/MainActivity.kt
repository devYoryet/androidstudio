package com.example.mysumativa

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.mysumativa.navigation.NavGraph
import com.example.mysumativa.ui.theme.MysumativaTheme
import java.util.*

class MainActivity : ComponentActivity() {

    // Instancia de Text-to-Speech
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar TextToSpeech
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.getDefault()
            }
        }

        setContent {
            MysumativaTheme {
                val navController = rememberNavController()
                // Aquí pasamos la instancia de MainActivity
                NavGraph(
                    navController = navController,
                    mainActivity = this@MainActivity,  // Pasamos la instancia de MainActivity
                    tts = tts
                )
            }
        }
    }

    // Limpiar Text-to-Speech al destruir la actividad
    override fun onDestroy() {
        tts.shutdown() // Cierra TTS para liberar recursos
        super.onDestroy()
    }
}
