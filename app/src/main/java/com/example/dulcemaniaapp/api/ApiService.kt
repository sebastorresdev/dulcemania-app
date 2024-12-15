package com.example.dulcemaniaapp.api

import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Pedido
import com.example.dulcemaniaapp.models.Producto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("productos") // Asegúrate de poner la URL correcta
    fun getProductos(): Call<List<Producto>>

    @GET("clientes") // Ruta del endpoint
    fun getClientes(): Call<List<Cliente>>

    @POST("pedidos")
    fun crearPedido(@Body createPedido: CreatePedido): Call<Pedido>

    @GET("pedidos")
    fun getPedidos(): Call<List<Pedido>>

}