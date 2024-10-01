package com.example.mysumativa.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysumativa.firebase.FirestoreUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CartaItem(
    val id: String = "",
    val nombre: String = "",
    val precio: Double = 0.0,
    val descripcion: String = "",
    val idCategoria: String = "",  // Campo de categoría
    var cantidad: Int = 0  // Campo adicional para manejar la cantidad localmente

)

class CartaViewModel : ViewModel() {

    private val _cartaItems = MutableStateFlow<List<CartaItem>>(emptyList())
    val cartaItems: StateFlow<List<CartaItem>> = _cartaItems

    private val _categorias = MutableStateFlow<List<String>>(emptyList())
    val categorias: StateFlow<List<String>> = _categorias

    // Estado para almacenar los productos seleccionados en el pedido
    private val _pedido = MutableStateFlow<List<CartaItem>>(emptyList())
    val pedido: StateFlow<List<CartaItem>> = _pedido

    init {
        // Cargar datos automáticamente cuando se crea el ViewModel
        loadCategorias()
        loadCartaItems()
    }

    // Cargar ítems de la carta desde Firestore con manejo de errores
    fun loadCartaItems() {
        viewModelScope.launch {
            try {
                val items = FirestoreUtils.getCartaItems() // Asegúrate de que esta función funcione correctamente
                _cartaItems.value = items // Actualiza el estado con los productos cargados
            } catch (e: Exception) {
                e.printStackTrace() // Maneja errores si la carga falla
            }
        }
    }

    // Cargar categorías desde Firestore con manejo de errores
    fun loadCategorias() {
        viewModelScope.launch {
            try {
                val categorias = FirestoreUtils.getCategorias() // Asegúrate de que esta función funcione correctamente
                _categorias.value = categorias // Actualiza el estado con las categorías cargadas
            } catch (e: Exception) {
                e.printStackTrace() // Maneja errores si la carga falla
            }
        }
    }

    // Función para agregar un producto al pedido
    fun agregarAlPedido(item: CartaItem) {
        val pedidoActual = _pedido.value.toMutableList()

        // Buscar si el producto ya está en el pedido
        val productoExistente = pedidoActual.find { it.id == item.id }

        if (productoExistente != null) {
            // Si ya está en el pedido, incrementa la cantidad
            productoExistente.cantidad += 1
        } else {
            // Si no está en el pedido, añádelo con cantidad 1
            pedidoActual.add(item.copy(cantidad = 1))
        }

        // Actualiza el estado del pedido con la lista modificada
        _pedido.value = pedidoActual
    }
    // Función para eliminar un ítem del pedido
    fun eliminarDelPedido(item: CartaItem) {
        val pedidoActual = _pedido.value.toMutableList()

        // Buscar si el producto ya está en el pedido
        val productoExistente = pedidoActual.find { it.id == item.id }
        if (productoExistente != null) {
            // Si la cantidad es mayor a 1, restamos 1
            if (productoExistente.cantidad > 1) {
                productoExistente.cantidad -= 1
            } else {
                // Si la cantidad es 1, eliminamos el producto del pedido
                pedidoActual.remove(productoExistente)
            }
        }

        _pedido.value = pedidoActual
    }
    // Calcular el total del pedido
    fun calcularTotalPedido(): Double {
        return _pedido.value.sumOf { it.precio * it.cantidad }  // Multiplicamos por la cantidad
    }

}
