package com.example.grace.model

import com.google.gson.annotations.SerializedName

data class CadastroRequest(
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String,
    @SerializedName("tipo_usuario") val tipoUsuario: String
)
