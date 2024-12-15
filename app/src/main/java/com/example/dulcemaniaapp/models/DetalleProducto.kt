package com.example.dulcemaniaapp.models

data class DetalleProducto(
    var id: Int,
    val nombre: String,
    var cantidad: Int,
    var precioUnitario: Double,
    var precioTotal: Double
)
