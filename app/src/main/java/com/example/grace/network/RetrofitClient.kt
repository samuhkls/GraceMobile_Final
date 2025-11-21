package com.example.grace.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Usamos 'object' para criar um Singleton (uma instância única)
object RetrofitClient {

    // --- IMPORTANTE ---
    // SUBSTITUA "SEU_IP_VAI_AQUI" PELO IPV4 QUE VOCÊ ENCONTROU.
    // DEVE INCLUIR "http://" NO COMEÇO E "/" NO FINAL.
    // Exemplo: "http://192.168.1.10/"
    private const val BASE_URL = "http://192.168.0.207/"

    // Criação "preguiçosa" (lazy) do objeto Retrofit.
    // Ele só será construído na primeira vez que for usado.
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // 1. Define a URL base (seu IP) [cite: 133]
            .addConverterFactory(GsonConverterFactory.create()) // 2. Define o conversor (Gson) [cite: 134]
            .build()
    }

    // Criação "preguiçosa" da nossa interface ApiService
    // Esta é a linha que "conecta" o Retrofit com a interface ApiService
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}