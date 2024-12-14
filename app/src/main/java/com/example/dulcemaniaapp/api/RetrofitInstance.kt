package com.example.dulcemaniaapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // URL base de la API
    private const val BASE_URL = "http://10.0.2.2:8070/api/"

    // Instancia de Retrofit
    val retrofitService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // URL base
            .addConverterFactory(GsonConverterFactory.create())  // Convertir respuestas JSON a objetos Kotlin
            .build()
            .create(ApiService::class.java)  // Crear la instancia de la interfaz ApiService
    }
}