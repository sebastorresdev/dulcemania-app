package com.example.dulcemaniaapp.services

import android.util.Log
import com.example.dulcemaniaapp.api.RetrofitInstance
import com.example.dulcemaniaapp.models.Usuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsuarioService {
    fun registrarUsuario(usuario: Usuario, callback: (Usuario?, String?) -> Unit) {
        RetrofitInstance.retrofitService.registrarUsuario(usuario).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful) {
                    val usuarioCreado = response.body()
                    if (usuarioCreado != null) {
                        callback(usuarioCreado, null)
                    } else {
                        callback(null, "La respuesta de la API no contiene el usuario creado.")
                    }
                } else {
                    callback(null, "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("UsuarioService", "Error al crear el usuario: ${t.message}")
                callback(null, "Error: ${t.message}")
            }
        })
    }
}