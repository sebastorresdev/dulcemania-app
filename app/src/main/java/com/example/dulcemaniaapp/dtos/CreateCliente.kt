package com.example.dulcemaniaapp.dtos

data class CreateCliente(
    val tipoDocumento:String,
    val numeroDocumento : String,
    val razonSocial : String,
    val contacto:String,
    val telefono: String,
    val paginaWeb: String,
    val correo:String,
    val direccionClientes: List<CreateDireccion>
)
