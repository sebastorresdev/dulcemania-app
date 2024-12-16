package com.example.dulcemaniaapp.api

import com.example.dulcemaniaapp.dtos.CreateCliente
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Pedido
import com.example.dulcemaniaapp.models.Producto
import com.example.dulcemaniaapp.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("productos") // Asegúrate de poner la URL correcta
    fun getProductos(): Call<List<Producto>>

    @GET("clientes") // Ruta del endpoint
    fun getClientes(): Call<List<Cliente>>

    @POST("clientes")
    fun crearCliente(@Body createCliente: CreateCliente): Call<Cliente>

    @GET("pedidos/{identificador}")
    fun getPedidos(@Path("identificador")idenficador:String): Call<List<Pedido>>

    @POST("pedidos")
    fun crearPedido(@Body createPedido: CreatePedido): Call<Pedido>

    @POST("usuarios")
    fun registrarUsuario(@Body usuario: Usuario): Call<Usuario>
}