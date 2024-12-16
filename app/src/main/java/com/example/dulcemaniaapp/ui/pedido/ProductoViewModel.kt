package com.example.dulcemaniaapp.ui.pedido

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulcemaniaapp.dtos.CreateDetallePedido
import com.example.dulcemaniaapp.models.DetalleProducto

class ProductoViewModel : ViewModel() {
    val productosSeleccionados = MutableLiveData<List<DetalleProducto>>()

    init {
        // Inicializar la lista como vacía
        productosSeleccionados.value = emptyList()
    }

    fun actualizar(productos: List<DetalleProducto>) {
        limpiarLista()
        agregarProducto(productos)
    }

    // Función para limpiar la lista de productos seleccionados
    private fun limpiarLista() {
        productosSeleccionados.value = emptyList()
    }

    // Función para agregar un producto a la lista
    private fun agregarProducto(producto: List<DetalleProducto>) {
        // Crear una nueva lista con los productos existentes más el nuevo
        val listaActualizada = productosSeleccionados.value.orEmpty().toMutableList()
        listaActualizada.addAll(producto)
        productosSeleccionados.value = listaActualizada
    }

    // Función opcional para eliminar un producto específico de la lista
    private fun eliminarProducto(producto: DetalleProducto) {
        val listaActualizada = productosSeleccionados.value.orEmpty().toMutableList()
        listaActualizada.remove(producto)
        productosSeleccionados.value = listaActualizada
    }

    fun obtenerCreateDetallesPedidos(): List<CreateDetallePedido> {
        return productosSeleccionados.value?.map { dp -> CreateDetallePedido(dp.id, dp.cantidad) }!!.toList()
    }
}