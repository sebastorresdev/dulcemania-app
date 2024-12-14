package com.example.dulcemaniaapp.api

import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Producto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("productos") // Asegúrate de poner la URL correcta
    fun getProductos(): Call<List<Producto>>

    @GET("clientes") // Ruta del endpoint
    fun getClientes(): Call<List<Cliente>>
}