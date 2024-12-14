package com.example.dulcemaniaapp.services

import com.example.dulcemaniaapp.api.RetrofitInstance
import com.example.dulcemaniaapp.models.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoService {

    // Método para obtener los productos desde la API
    fun obtenerProductos(callback: (List<Producto>?, String?) -> Unit) {
        // Llamada a la API utilizando Retrofit
        RetrofitInstance.retrofitService.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    // Si la respuesta es exitosa, pasamos la lista de productos al callback
                    val listaProductos = response.body()
                    if (listaProductos != null) {
                        callback(listaProductos, null)  // Pasamos la lista de productos y sin mensaje de error
                    } else {
                        callback(null, "La respuesta de la API no contiene productos.")  // Error: Cuerpo vacío
                    }
                } else {
                    // Si la respuesta no es exitosa, podemos manejar el error aquí
                    callback(null, "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                // Manejo de errores si la llamada falla (por ejemplo, error de red)
                callback(null, "Error de conexión: ${t.message}")
            }
        })
    }
}
