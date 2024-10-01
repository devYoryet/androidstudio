package com.example.mysumativa

import org.junit.Test
import org.junit.Assert.*

// Clase que valida datos como dirección y comuna
class DeliveryValidator {
    fun validateAddress(address: String): Boolean {
        return address.isNotEmpty()
    }

    fun validateComuna(comuna: String): Boolean {
        return comuna.isNotEmpty()
    }
}

class ExampleUnitTest {

    private val validator = DeliveryValidator()

    @Test
    fun address_isValid() {
        // Prueba con una dirección válida
        val result = validator.validateAddress("Calle Falsa 123")
        assertTrue(result) // La dirección no está vacía, debe ser válida
    }

    @Test
    fun address_isInvalid() {
        // Prueba con una dirección vacía
        val result = validator.validateAddress("")
        assertFalse(result) // La dirección está vacía, debe ser inválida
    }

    @Test
    fun comuna_isValid() {
        // Prueba con una comuna válida
        val result = validator.validateComuna("Comuna Falsa")
        assertTrue(result) // La comuna no está vacía, debe ser válida
    }

    @Test
    fun comuna_isInvalid() {
        // Prueba con una comuna vacía
        val result = validator.validateComuna("")
        assertFalse(result) // La comuna está vacía, debe ser inválida
    }

    @Test
    fun addition_isCorrect() {
        // Prueba básica para confirmar que las operaciones matemáticas están bien
        assertEquals(4, 2 + 2)
    }
}
