package com.example.mysumativa

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mysumativa", appContext.packageName)
    }

    @Test
    fun validateResourcesAccess() {
        // Prueba para verificar el acceso a los recursos de la aplicación
        val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val appName = appContext.getString(appContext.applicationInfo.labelRes)
        assertEquals("mysumativa", appName.lowercase()) // Verifica el nombre de la app
    }
}
