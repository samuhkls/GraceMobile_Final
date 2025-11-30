package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Long,

    @SerializedName("nome") val nome: String,

    @SerializedName("email") val email: String,

    @SerializedName("tipo_usuario") val tipoUsuario: String
)
