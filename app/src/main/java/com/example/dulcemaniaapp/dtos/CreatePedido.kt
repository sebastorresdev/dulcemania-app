package com.example.dulcemaniaapp.dtos

data class CreatePedido(
    val idDireccion: Int,
    val idMedioPago: Int,
    val idUsuario:Int,
    val observacion: String,
    val tipoDocumento:String,
    val detallePedidos:  List<CreateDetallePedido>
)
