package com.example.dulcemaniaapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dulcemaniaapp.models.ItemProducto

class ProductoViewModel : ViewModel() {
    private val _total = MutableLiveData<Double>(0.0) // Total del precio
    val total: LiveData<Double> get() = _total

    private val _cantidadTotal = MutableLiveData<Int>(0) // Total de cantidad
    val cantidadTotal: LiveData<Int> get() = _cantidadTotal

    // Método para actualizar el total y la cantidad total
    fun actualizarTotales(productos: List<ItemProducto>) {
        val nuevoPrecioTotal = productos.sumOf { it.precio * it.cantidad }
        val nuevaCantidadTotal = productos.sumOf { it.cantidad }

        _total.value = nuevoPrecioTotal
        _cantidadTotal.value = nuevaCantidadTotal
    }

    // Método para agregar un producto
    fun agregarProducto(productos: MutableList<ItemProducto>, producto: ItemProducto) {
        productos.add(producto)
        actualizarTotales(productos)
    }

    // Método para eliminar un producto
    fun eliminarProducto(productos: MutableList<ItemProducto>, position: Int) {
        productos.removeAt(position)
        actualizarTotales(productos)
    }

    // Método para actualizar la cantidad de un producto
    fun actualizarCantidad(productos: MutableList<ItemProducto>, position: Int, nuevaCantidad: Int) {
        productos[position].cantidad = nuevaCantidad
        actualizarTotales(productos)
    }

}