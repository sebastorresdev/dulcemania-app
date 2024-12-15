package com.example.dulcemaniaapp.models

data class Pedido(
    val id: Int,
    val fecha: String,
    val estado: String,
    val total: Double,
    val nombreVendedor: String,
    val nombreCliente: String,
    val distrito: String,
    val direccion: String,
    val observacion: String,
    val tipoDocumento: String,
    val ruc: String,
    val medioPago: String,
    val numeroDocumento: String?,
    val detallePedidos: List<DetallePedido>
)
