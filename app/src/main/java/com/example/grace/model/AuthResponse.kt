package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token") val token: String,

    // Note que estamos REUTILIZANDO a classe User que criamos antes.
    @SerializedName("user") val user: User
)
