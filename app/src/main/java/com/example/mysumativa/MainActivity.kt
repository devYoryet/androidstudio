package com.example.mysumativa

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.mysumativa.navigation.NavGraph
import com.example.mysumativa.ui.theme.MysumativaTheme
import java.util.*
import android.util.Log
class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar TextToSpeech
        tts = TextToSpeech(this, this)

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController, mainActivity = this, tts = tts!!)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Cambiar el idioma a español
            val result = tts?.setLanguage(Locale("es", "ES"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "El idioma seleccionado no es soportado.")
            } else {
                isTtsInitialized = true
            }
        } else {
            Log.e("TTS", "Inicialización del TextToSpeech fallida.")
        }
    }

    override fun onDestroy() {
        // Libera los recursos de TextToSpeech al destruir la actividad
        tts?.let {
            it.stop()
            it.shutdown()
        }
        super.onDestroy()
    }

    fun speakText(text: String) {
        if (isTtsInitialized && tts != null) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Log.e("TTS", "TextToSpeech no está inicializado.")
        }
    }
}

