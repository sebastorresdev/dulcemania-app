package com.example.dulcemaniaapp.models

data class Direccion(
    val id: Int,
    val direccion: String,
    val distrito: String,
    val referencia: String
)
{
    override fun toString(): String {
        return direccion
    }
}
