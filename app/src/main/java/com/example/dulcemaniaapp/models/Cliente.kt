package com.example.dulcemaniaapp.models

data class Cliente(
    val id: Int,
    val razonSocial: String,
    val ruc: String,
    val personaContacto: String,
    val telefono: String,
    val correo: String,
    val direcciones: List<Direccion>
)
