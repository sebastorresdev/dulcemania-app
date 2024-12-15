package com.example.dulcemaniaapp.models

data class Producto(
    val id: Int,
    val descripcion: String,
    val familia: String,
    val marca: String,
    val esActivo: Boolean,
    val precioUnitario: Double,
    val stock: Int,
    val urlImg: String,
    val stockMinimo: Int,
    val codigoInterno: String,
    val codigoBarras: String,
    val unidadMedida: String
){
    override fun toString(): String {
        return descripcion
    }
}
