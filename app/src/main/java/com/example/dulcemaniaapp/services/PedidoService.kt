package com.example.dulcemaniaapp.services

import android.util.Log
import com.example.dulcemaniaapp.api.RetrofitInstance
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoService {

    fun crearPedido(pedido: CreatePedido, callback: (Pedido?, String?) -> Unit) {
        RetrofitInstance.retrofitService.crearPedido(pedido).enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                if (response.isSuccessful) {
                    val pedidoCreado = response.body()
                    if (pedidoCreado != null) {
                        callback(pedidoCreado, null)  // Pedido creado correctamente
                    } else {
                        callback(null, "La respuesta de la API no contiene el pedido creado.")
                    }
                } else {
                    callback(null, "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                Log.e("PedidoService", "Error al crear el pedido: ${t.message}")
                callback(null, "Error: ${t.message}")
            }
        })
    }

    fun obtenerPedidos(callback: (List<Pedido>?, String?) -> Unit) {
        RetrofitInstance.retrofitService.getPedidos().enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>
            ) {
                if (response.isSuccessful) {
                    val listaClientes = response.body()
                    if (listaClientes != null) {
                        callback(listaClientes, null)
                    } else {
                        callback(null, "La respuesta de la API no contiene los pedidos.")
                    }
                } else {
                    callback(null, "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                Log.e("PedidoService", "Error al obtener los pedidos: ${t.message}")
                callback(null, "Error: ${t.message}")
            }
        })
    }
}