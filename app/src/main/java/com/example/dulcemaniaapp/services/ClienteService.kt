package com.example.dulcemaniaapp.services

import android.util.Log
import com.example.dulcemaniaapp.api.RetrofitInstance
import com.example.dulcemaniaapp.dtos.CreateCliente
import com.example.dulcemaniaapp.dtos.CreatePedido
import com.example.dulcemaniaapp.models.Cliente
import com.example.dulcemaniaapp.models.Pedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteService {

    fun obtenerClientes(callback: (List<Cliente>?, String?) -> Unit) {

        RetrofitInstance.retrofitService.getClientes().enqueue(object : Callback<List<Cliente>> {
            override fun onResponse(call: Call<List<Cliente>>, response: Response<List<Cliente>>
            ) {
                if (response.isSuccessful) {
                    val listaClientes = response.body()
                    if (listaClientes != null) {
                        callback(listaClientes, null)  // Pasamos la lista de productos y sin mensaje de error
                    } else {
                        callback(null, "La respuesta de la API no contiene clientes.")  // Error: Cuerpo vacío
                    }
                } else {
                    callback(null, "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Cliente>>, t: Throwable) {
                Log.e("ClienteService", "Error al obtener clientes: ${t.message}")
                callback(null, "Error: ${t.message}")
            }
        })
    }

    fun crearCliente(cliente: CreateCliente, callback: (Cliente?, String?) -> Unit) {
        RetrofitInstance.retrofitService.crearCliente(cliente).enqueue(object : Callback<Cliente> {
            override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                if (response.isSuccessful) {
                    val clienteCreado = response.body()
                    if (clienteCreado != null) {
                        callback(clienteCreado, null)  // Pedido creado correctamente
                    } else {
                        callback(null, "La respuesta de la API no contiene el pedido creado.")
                    }
                } else {
                    callback(null, "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Cliente>, t: Throwable) {
                Log.e("PedidoService", "Error al crear el pedido: ${t.message}")
                callback(null, "Error: ${t.message}")
            }
        })
    }

}