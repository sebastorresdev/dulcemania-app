package com.example.dulcemaniaapp.api

import com.example.dulcemaniaapp.models.Producto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("productos") // Asegúrate de poner la URL correcta
    fun getProductos(): Call<List<Producto>>
}